# Architecture Plan
## If you were to develop the same application where the temperatures dataset grows by 1Go per minute, what would you do differently?

First, I would set a staging area to store the incoming data in raw format and compress it. It is mainly plain text so it is easily compressible. I would use the Avro format for that as it is efficient to write a massive amount of data. The data would be partitioned on a technical date. This partitioning can be put to a good use if you need to reprocess some data with different rules.

Then I would use a data engine like MapReduce or Spark to process this raw data and also to filter it in order to keep only what is needed for our computation. The data might grow rapidly but most of is not useful for our usecase.

Filtering data is easily scalable so it does not matter if there is a huge amount of data. After doing so, I would partition the data by date and region before the final processing, in a columnar format such as Parquet or ORC. Those formats depend on the platform and the engine that are used (Parquet if we use Spark, ORC with Hive and other with the Google Cloud Platform).

A tool like Indexima (which stores indexes in-memory) could be useful to replace a relational database in the end of the pipeline as it will aggregate data on very large sources.

![](/images/schema1.png)

My ideal pipeline would be:
1. Staging area for storing source date in raw format (compressed and partioned)
2. Processing on the data plaform
3. Store the clean and detailed data on the data platform (with a columnar format so it can be used by a tool, for instance Hive)
4. Push the aggregated data to a relational database or use it directly with a tool like Indexima.

In my opinion, depending on the needs, an interface with this amount of data going over HTTP is not the best choice for batching. I would rather use a real-time pipeline with a Kafka Cluster to handle the incoming load. 

## If our data source was to emit multiple versions (corrections for instance) of the same data, what could be the different applicable strategies?

I believe that it depends on the purpose of the final data. 

If the data is used nearly in real-time, it will not be necessary to apply the corrections : nobody will ever need to see those.

However, if the "final" data has to be up-to-date and potentially handle as an historical data, it could be interesting to apply the changes on the detailed data or even to record all the versions of the changes. In order to do that, a primary key has to be defined on our data on which the changes can be tracked. An SCD (Slow Changing Dimension) pattern can be applied  so that every change is recorded and has a start/end date.

![](/images/scd_example.gif)

Still, a question remains: where will thoses changes be recorded? I would suggest to record it either on our final relational database only or both in the detailed table and the relational table.

## What infrastructure, products, workflow scheduler, would you use to make sure the whole pipeline runs fine in production?

I would run the pipeline on the cloud, but an Hadoop cluster could work too. 

I would choose:
- Google Cloud Platform / Hadoop on premise (most of my experience)
- Workflow scheduler : AirFlow / ControlM
- Data Engine : DataFlow or Spark
- Datawarehousing : BigQuery / Hive (or even parquet files with SparkQL)
- Relational Database : PostgreSQL, as it is truly open source and more stable that MySQL for heavy-loads. This specific database can be a SPOF if high availability is not set.
- Storage : Google Cloud Storage / HDFS

To ensure that everything runs smoothly, I believe important to gather metrics of each component used and monitor the whole pipeline:
- computation duration (in real-time but also over time)
- data statistics (volume of new data, processed data for each step etc.)
- storage evolution
- system metrics (CPU/memory used by container/server, networking)

For this specific purpose, I would use a "classic" EFK stack (Elastic/Filebeat/Kibana (or Grafana)) to gather all of the metrics and monitor the pipeline.

Those metrics are crucial to run on premise as you can tune the stack to make the most of the hardware.

## Some months later, we think to apply another aggregation/model to the input data. How would your architecture evolve to integrate this challenge?

I would create a second pipeline that would read the same Avro files that are stored. 

This pipeline would be about the same, except that a change would occur on the filtering step. Also, I could partition differently to suit the needs. If the aggregation is different, then a detailed table could be used in the data lake and be computed with a new model.
