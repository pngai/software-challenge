## Q1
    For the Immutable Queue implementation, I use 2 Immutable Stack to emulate it and the idea is taken from https://blogs.msdn.microsoft.com/ericlippert/2007/12/10/immutability-in-c-part-four-an-immutable-queue/ 

## Q2
### Constraints:

    - At least 1,000,000,000 writes per day
    - At least 1,000,000 users perform reads/queries
    - Queries are time series metrics
    - Provide metrics to users with at most one hour delay
    - Mimimum down time
    - Ability to reprocess data

### Estimations:
    
    Google analytics payload limits is about 8k bytes. So assume users on average send requests each containing 4k bytes to our system.
    With the number of writes specified, we will be expecting 1000000000 * 4k = 4,000,000,000,000 = 4TB
    We will be expecting to store at least 4TB of data each day due to the need to reprocess data upon request.

    Assume we will retain data for 1 year, then we need 4TB * 365 = 1460 TB. So we may need to store it with cheap storage offline.

    Assume each user perform 100 queries each day. 
    Then our system need to handle 1000000 * 100  = 100M queries each day.
    Therefore QPS is roughly 1000 per second.

### Design Goals:
    
    Since we want to acheive minimum down time which means high availability.
    By the CAP theorem, We choose network partition and availablity and sacrifice consistancy.

### Design Details
![Alt text](architecture.png?raw=true "architecture")
#### Write events
    We have kafka rest proxy infront of distributed message queue kafka to handle billions write event each day. The exact number of kafka instance needed will be determined by actually benchmarking it. Using message queue in this way is ideal because each requests coming from users can be add to the queue for processing.

    Kafka can process each write event and store the raw and processed data in a time series database such as TimescaleDB.  TimescaleDB is chosen becasue it can offers a rich set of SQL to developers, High Availability. More important, TimescaleDB is automatically sharded and has no influence from the users’ perspective. When the amount of data is very large, the write performance does not deteriorate as much as using plain postgresql.

    The system also can handle the 1460TB data for reprocessing purpose. Basically, raw data that is older than 1 month are moved from TimescaleDB to cheap storage. If reprocessing is needed it can be loaded from cheap storage and run offline then store back the result to TimescaleDB.


#### Read/Query
    Users' queries will pass through loadbalances to web servers. This is to distribute load to each web servers. Web/App servers serve web application which may be a dashboard show those time series metrics to users. Behind the scenes the web appliation will make api to query TimescaleDB for data. It may be attacched with in-memory cache such as redis to store hot queries to reduce latency.

#### Data storage
    User query patterns are time-series related metrics so data stored will most likely be Time series data. As the estimattion for data storage is huge scalability is a concern. To optimize for time series metrics we may consider store them using timeseries database such as TimescaleDB. The number of instance needed can be determined by measurement in real case.

#### High availability
    Kafka and web/app servers are running in multiple instances. So load balancer will route traffic to the instance that is alive. We need to configure multiple TimescaleDB instances to make sure when one instance is dead another can continue to serve.



