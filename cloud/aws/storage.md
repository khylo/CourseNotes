 # S3	
	Spread across multipe AZs
	Key value store.
	Buckets (like folders), and thewn objects and subfolders in them
	Key = name
	Value = data  upto 5 TB
	VersionId
	MetaData
	SubResources
		ACLs
		Torrents

	returns 200 response afer success upload

	99.99 availability guarantee (built for 99.99 availability)
	99.999999999 (11 9's) durability// designed to sustain loss of 2 faclities concurently)

	Tiered storage (e.g. move after time to glacier)
	Encryption
	Control List and Bucket policies

	s3, S3IA, S3 IA 1 zone, expedited, bulkaz, glacier (3 retrieveal types s,  3 - 5 hrs.
	bucket is like a folder but unique and routable to  

 * Amazon S3 supports both virtual-hosted–style and path-style URLs to access a bucket. *
 	Virtual Host: http://bucket.s3-aws-region.amazonaws.com e.g. http://my-pollylambda-website.s3-eu-west-1.amazonaws.com/  (Once bucket has public permissions to read and list)
	Path like: http://s3-aws-region.amazonaws.com/bucket e.g. http://s3-eu-west-1.amazonaws.com/my-pollylambda-website
	
	Can setup S3 as server for static website (via console or)
	aws s3 website s3://my-bucket/ --index-document index.html --error-document error.html

	Data conistency
		REad after write for consistency for puts for new objects
	   Eventual consistency for update puts or deletes.. 
	read s3 faq

## Types
 * S3
 * EFS (NFS)
 * Glacier 
 * Snowball 
 * Storage Gateway (4 types) 

## Cross Region Replication
* Configured at bucket level
* Must enable versioning
    Only changes after replication turned on are replicated, so existing bucket structure is not (use cli)
    deletes are replicated
        delete of delete flag is not (so if you undo a delete that is not transferred)
        delete of older versions is not
    Can't replicate to multiple buckets yet.
    Used for increasing redundancy in case pof region failure, not for performance of retrieving files. Use cloudfront for this.

## Lifecycle mgmt .. versioning NOT needed
    S3 -> S3-IA -> glacier 
must be >128kb in size and 30 days (or more) after 
Can have delete rule after x time


## Data conistency
   Read after write for consistency for puts for new objects
   Eventual consistency for update puts or deletes.. 

## read s3 faq

Spread across multipe AZs
Key value store.
Key = name
Value = data  upto 5 TB min size 0
VersionId
MetaData
SubResources
    ACLs  Access control List (for access to objects).. Also Bucket policy for entire bucket policy
    Torrents

get 200 response after success upload
Can turn on MFA delete. Which means you have to multifactor confirm if you want to delete. Good if you want to protect against accidental deletes.

99.99 availability guarantee (built for 99.99 availability)
99.999999999 (11 9's) durability// designed to sustain loss of 2 faclities concurently)

### s3 Performance
https://aws.amazon.com/about-aws/whats-new/2018/07/amazon-s3-announces-increased-request-rate-performance/
Is hashing needed anymore
	S3 can achieve at least 3,500 PUT/POST/DELETE and 5,500 GET requests per second PER PREFIX IN A BUCKET.
	So if there are a lot of read writes one way to increase performance is to use hexidecial hash (e.g. md5) PREFIX to  bucket names to parallelize this
	
	for reads only CloudFront can improve performance since it servers a cached version of doc.
	
	Faster upload if you allow multipart files for big files.
	Also to increase performance you can use hexidecial hash (e.g. md5) PREFIX to 

IA Infrequent access. Pay for retrieval 
RRS Reduced Redundancy storage (is this IA 1 zone?)
    availability 99.99   Durability 99.99 (4 9s intead of 11)

Upload objects in a single operation—With a single PUT operation, you can upload objects up to 5 GB in size. ...
Upload objects in parts—Using the multipart upload API, you can upload large objects, up to 5 TB.
    

Tiered storage (e.g. move after time to glacier)
Encryption 4 types, client and 3 server side .. 
        sse-s3 (aes), 
        sse-kms (key managed service.. extra features and costs to a sse-s3) 
        sse- c(customer supplied keys)

## Security
    2 ways.. 
        1 Bucket policy (for all bucket)
        2 ACL .. for any object

    4 types of encryption for S3 (see above) at rest
    in transit use https


## Storage Gateway
    Software appliance (virtual machine interface) that you run on premises and it copies data to Amazon S3 or glacier
    Connect via internet/ Direct Gatway of vpcs
    4 types
       1  File Gateway NFS
            files stored in S3 .. ownership, permissions and timestamps stored durably as metadata
            accessed via NFS
        Volume Gateway iSCSI  block based storage.. not S3. Like virtual harddisks.. 
        2     Stored volumes.. Appliance is gateway iSCSI between application and disks/ AWS
                Data stored on premises (you provide disks up to 15TB) and backed up incrementally to EBS
        3    Cached Volumes only most recent data stored locally.. Appliance is gateway iSCSI between application and disks/ AWS
                Data stored on premises (you provide disks up to 34TB) and backed up incrementally to S3 
        4 Tape Gateway.. Virtual Tape Library VTL can use lifecycle managment to goto glacier
            Appliance interfaces with backup software/ platform such as Veeam, NetBackup.. Acts like a tape drive but sends data to AWS. (instead of creating loads of tapes which then need to be stored)

## Running a site from S3
    Domain name and bucket name must be the same if you want to use Route53 dns routing
So url of S3 = <bucketName>.s3-website-<region>.amazonaws.com
    Under properties can set static wbsire.. Set landing page , error page and redirect rules

might be differnt for website urls.. 
See https://stackoverflow.com/questions/26604977/url-for-public-amazon-s3-bucket
My bucket is now visible as http://bucket.s3-website-us-east-1.amazonaws.com/

I see others refer to their bucket as http://s3-us-east-1.amazonaws.com/bucket/



## Snowball
    Used to be Import export.. Send them physical disk and they upload it 
    Can import from S3 or export to S3
     Snowball 
        Storage appliance with security etc upto 80Tb
    Snowball Edge
        Storage and compute appliance with security etc      up to 100TB
        Can run lambda on it.. e.g. on planes. take measurements and then ship to AWS  
    Snowmobile
        Truck.. Petabyte and exabyte of data   100PB per truck 45 foot shipping container

## Glacier
    For archive storage that doesn't need to be accessed. (can be but takes time and costs)
    Can Retrieve 'Expedited' but costs more and must have 'provisioned capacity' created for storage of results
	
# Cloudfront => CDN
    Edge Location. Location where content will be cached  (not related to AZs or regions). Over 50 edge locations
    Origin , is origin of all files. can be S3 bucket, EC2 instance, Elastic Load Balance or route 53
        cAN HAVE MULTIPLE ORIGINS FOR A DISTRIBUTION
    
    Distribution is the CDN we create
    Web distribution for websites
    RTMP distribution for media streaming .. adobe flash
    can write to edge location i you knowit will be used.. will live until TTL expires (ttl in seconds)
    You can clear cached objects but you will be charged 

    REstrict user access. using singed urls or signed cookies
        WAf Web Application Firewall

## Route 53
Note whn working with Route53	bucket name and dns name must be same!!!
	

### Policy
Example policy (build using policy generator) fora llowing user access to buckt
{
  "Id": "Policy1540073941803",
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "Stmt1540073937763",
      "Action": "s3:*",
      "Effect": "Allow",
      "Resource": "arn:aws:s3:::khylo",
      "Principal": {
        "AWS": [
          "arn:aws:iam::064136172155:user/api"
        ]
      }
    }
  ]
}

# DataBases
Dynamo DB
Also DynamoDBMapper can be used to map to and from json

## RDS - OLTP .. Note scaling requires downtime
* Sql
* MySql
* PostgresSql
* Aurora   .. 2 copies of data contained in each AZ with min of 3 AZ's => *6 copies of data*
If you want more *copies of DB server running*, can create Aurora replaics
2 types of replicas A/ Aurora replicas (up to 15), mysql read replicas (5)
Aurora => self healing
* Maria DB

*Can turn on MultiAZ And read replicas.*
Multi AZ is an option in console. When you turn it on it creates backup in seperate AZ and failsover to it (including DNS update). Can also manually failover.
Read Replica is good for increasing performance
Can have read replicas of read replicas
Up to 5 read replicas

## NoSql
* DynamoDB   .. Push button scaling (easier than RDS) without downtime
SSD storage.. 3 geographic data centers
2 options. 1/ Eventual Consistent or 2/ strongly consistent options
* Neptune .. graphDb

## OLAP .. RedShift
Single Node up to 160 GB
Multi Node.. Leader and Compute notes... up to 128 compute nodes

## Elasticache
MemcacheD, redis
