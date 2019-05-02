# Architecture Plan
## If you were to develop the same application where the temperatures dataset grows by 1Go per minute, what would you do differently?

I would first set a staging area to store the incoming data in raw format and compress it. It is mainly plain text so it is easily compressible. I would use the Avro format for that as it is very efficient for writing massive data. The data would be partitioned on a technical date.

Then I would use a data engine like MapReduce or Spark to process this raw data, filter it to keep only what it is needed for our computation. The data might grow rapidly, most of it may not be useful for our usecase.

Filtering data is easily scalable so that will not be a problem if the data is huge. After filtering, I would partition the data by date and region before the final processing, in a columnar format like Parquet or ORC. This format might depend on the platform and the engine that are used (Parquet if we use Spark, ORC with Hive and other with Google Cloud Platform).

Also, a tool like Indexima (which stores indexes in-memory) might be useful to replace a relational database in the end of the pipeline as it will aggregate data on very large sources.

![](/images/schema1.png)

So my ideal pipeline would be:
1. Staging area for storing source date in raw format (compressed and partioned)
2. Processing on the data plaform
3. Store the clean and detailed data on the data platform (with a columnar format so it can be used by a tool like Hive for instance)
4. Push the aggregated data to a relational database or use it directly with a tool like Indexima

Depending on the needs, I think an interface with this amount of data going over HTTP might not be the right choice for batching. If possible, I would consider using a real-time pipeline with a Kafka Cluster to handle the incoming load. 

## If our data source was to emit multiple versions (corrections for instance) of the same data, what could be the different applicable strategies?

I think it really depends on what we do with the final data. 

If the data is used nearly in real-time, that will not be necessary to apply the corrections, because nobody will ever need to see them.

If we need historical data, then it becomes interesting to apply the changes on the actual data or even to record all the versions of the changes. In that case, we need to define a key on our data on which we can track the changes. We might apply a SCD pattern (Slow Changing Dimension) so that every change is recorded and has a start/end date.

![](/images/scd_example.gif)

The only question is where we want to record the changes: it might be more difficult in the detailed table than in a standard relational database.

## What infrastructure, products, workflow scheduler, would you use to make sure the whole pipeline runs fine in production?

I would probably use a Cloud Platform, but an Hadoop cluster could also do the job.

What I would choose:
- Google Cloud Platform / Hadoop on premise (most of my experience)
- Workflow scheduler : AirFlow / ControlM
- Database : PostgreSQL, as it is truly open source and more stable that MySQL for heavy-loads
- Data Engine : DataFlow or Spark
- Storage : Google Cloud Storage

It is important to gather metrics of each component used and monitor the whole pipeline:
- computation duration (in real-time but also over time)
- data statistics (volume of new data, processed data for each step etc.)
- storage evolution
- system metrics (CPU/memory used by container/server, networking)

Those metrics are crucial if we run on premise, as a Cloud Platform will mostly handle that for us (hopefully).

## Some months later, we think to apply another aggregation/model to the input data. How would your architecture evolve to integrate this challenge?

I would create a second pipeline that would read the same Avro files that are stored. 

This pipeline would be about the same, except we need to change the filtering step and maybe partition differently to suit our needs. If the aggregation is different, then we just have to use our detailed table in the data lake and compute it with our new model.
