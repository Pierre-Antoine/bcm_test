# Architecture Plan
## If you were to develop the same application where the temperatures dataset grows by 1Go per minute, what would you do differently?

I would first set a staging area to store temporarily the incoming data and compress it. It is mainly plain text so it is easily compressible. I would use the Avro format for that as it is very efficient for writing massive data. 

Then I would use a data engine like MapReduce or Spark to process this raw data, filter it to keep only what it is needed for our computation. The data might grow rapidly, most of it may not be useful for our usecase.

Filtering data is easily scalable so that will not be a problem if the data is huge. The Problem in our usecase is that we need to calculate the mean temperature by region of France, so the computation for each region cannot be run in parallel. That is why after filtering, I would partition the data by region for the final processing.

![](/images/schema1.png)

## What infrastructure, products, workflow scheduler, would you use to make sure the whole pipeline runs fine in production?

I would probably use a Cloud Platform, but an Hadoop cluster could also do the job.

