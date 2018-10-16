# High Level Overview
	## AWS Global Infrastructure
	..* REgions, AZs, CloudFront
	
	## Compute  **
	..* EC2
	..* EC2 Container Service (ECS)
	..* Elastic Beanstalk (in developper associate exam)
	..* Lambda
	..* LightSail VPS service.. Easier th
	..* Batch  
	
	## Storage  **
	..* S3 
	
	## Databases **
	..* RDS (aurora/ mysql etc)
	..* DynamoDB (noSql)
	..* Elasticache
	..* Red Shift ... Data warehouse
	
	## Migration **
	..* Aws Migration Hub
	..* Application Discovery Service (tracking depndency for apps)
	..* Database Migration Service (migration in house DBs to AWS)
	..* Server Migration Service
	..* Snowball
	
	## Network and Content Delivery **
	..* VPC
	..* CloudFront  .. CDN
	..* Route 53  .. DNS
	..* API Gateway (mmore on developer) Create api for other serives to talkj to
	..* Direct Connect (connet to AWS)
	
	## Devloper Tools
	..* Code STar .. Project managing code . .with CDN tool chain 
	..* Code Commit .. like Github
	..* Code Build  builds
	..* Code Deploy .. deploys to Ec2 or in house
	..* Code pipeline
	..* XRay. .analyse serverless 
	..* Cloud 9.. IDE env so you can build directly into cloud
	
	## Management Tool **
	..* CloudWatch   monitoring 
	..* CloudFormation   scripting infrastructure into code like firewalls 
	..* CloudTrail logging / auditing changes to AWS environment (1 week by default)
	..* Comnfig .. Keeps snapshots of AWS env and view
	..* opsWorks. .Uses chef and puppet to mage env
	..* Service Catalog .. Managing catalog of serivces approved for use use d for governance	
	..* Systems Manager .. Patch maintenance accross servers .. Group all resources by dept etc.
	..* Trusted Advisor .. gives advice e.g. ports open..
	..* Managed Services .. Will look after Ec2 etc.
	
	## Media Services
	..* Elastic Transcoder ... media trnsforming
	..* Media Convert .. Video transcoding 
	..* Media Live .. Live video processing service
	..* Media Package
	..* Media Store
	..* Media Tailor
	
	## Machine Learning 
	..*  SageMake .. Deep learning (neural nets)
	..*  Comprehend .. Sentiment analysis
	..*  Deep Lens  .. Camera with smarts e.g.g face recognition
	..*  Lex .. Alexa  AI chatbot
	..*  Machine learning e.g. regression, e.g. recommendation
	..*  Polly .. takes text and turns into speech
	..*  Rekognition.. upload image/video. .It recognises items in media .. pe
	..*  Amazon Translate
	..*  Transcribe .. Automatic speech reconition.. Speech to text
	
	## Analytics **
	..*  Athena  run sql queries against S3   serverless
	..*  EMR Elastic Map Reduce
	..*  CloudSearch
	..*  Elastic Search
	..* Kinesis .. used in big data. .Injest large amount of data into AWS e.g. tweets
	..* Kinesis Video Stream
	..* QuickSight  Business Intelligence tool.. 
	..* Data Pipeline. Move data between AWS service
	..* Glue .. ETL  .. maybe middware Soa?
	
	## Security Identity and Compliance **
	..* IAM
	..* Cognito   device authentication e.g. mobile apps, facebook, gmail , etc, use cognito to reguest temproary access
	..* GuardDuty. Monitors for amlicious activity on AWS
	..* Inspector. .Agent that runs and checks for vulnerabilities.
	..* Macie. Scan S3 buckets and look for PPI (personally Identifieble Information)
	..* Certificate Manage SSL certs
	..* CloudHSM Hardware security Modules.. store keys
	..* Directory Service. .Integrate Active Directory into IAM
	..* WAF Web access firewall. XXS, check for amicioul activity
	..* Shield   DDoS .. also shield advance 
	..* Artifact.. .For audit and compliance.. docs from amaozon
	
	## Mobile Serices
	..* Mobile Hub
	..* Pinpoint. .push notificatoins to mobile users e.g. 
	..* App Sync.. Updates data in web and mobile apps
	..* Device Farm.. test on different devices
	..* Mobile Analytics
	..*
	
	## AR/ VR   Augment Reality.. Virtual Reality .. 
	..* Sumerian, tool and language for 3d ar vr modelling
	
	## Application Integration **
	..* STep Functions
	..* Amazon MQ
	..* SNS .. Notificaition service	 
	..* SQS .. Queue service 
	..* SWF .. Simple Workslow service.. used by amazon shop floor.
	
	## Customer Engagement
	..* Connect .. Call center in cloud. 
	..* Simple Email Service.. Bulk emails
	
	## Business Productivity
	..* Alexa for Business, e.g. book meeting room, talk to IT
	..* Chime .. Video conferencing
	..* Work Docs.. dropbox for AWS
	..* Workmail.. like gmail
	
	## Desktop and App Streaming **
	..* Workspaces  .. Virtual Desktop
	..* App Streaming .. streaming application. .Running in cloud but streamed to device.. Like citrix.
	
	## IOT
	..* IOT Device Management, including machine learning
	..* FreeRTOS.. Free OS for microcontrollers
	..* Greengrass 
	
	## Games
	..* GameLift.
	
	

# IAM
	AWS: Identity Access maanagement. .. Global (not tied to a region)
		Create users, group => permissions,  Groups policies.  
			User/ password for console, or accessKeyId / secret access key for programatic
			Can attach permissions individually, or through groups
			
			SEtup billing alert.
		Goto billing dashboard
		Setup receive billing aleryts
			click setup billing alert => CloudWatch page
			Goto Billing under alarms. Create alert

# S3	
	This email is from an external source - exercise caution regarding links and attachments.
	Spread across multipe AZs
	Key value sotre.
	Key = name
	Value = data  upto 5 TB
	VersionId
	MetaData
	SubResources
		ACLs
		Torrents

	et 200 response afer success upload

	99.9 availability guarantee (built for 99.99 availability)
	99.999999999 (11 9's) durability// designed to sustain loss of 2 faclities concurently)

	Tiered storage (e.g. move after time to glacier)
	Encryption
	Control List and Bucket policie

	s3, S3IA, S3 IA 1 td, expedited, bulkaz, glacier (3 retrieveal types s,  3 - 5 hrs.
	bucket is like a folder but unique and routable to  

	bucket corresponds to url... https://s3-amazonaws.com/<name> e.g. https://s3-eu-ireland
	s3 global though??

	Data conistency
		REad after write for consistency for puts for new objects
	   Eventual consistency for update puts or deletes.. 
	read s3 faq

## Types
 ..* S3
 ..* EFS (NFS)
 ..* Glacier 
 ..* Snowball 
 ..* Storage Gateway (4 types) 

##Cross Region Replication
    Must enable versioning
    Only changes after replication turned on are replicated, so existing bucket structure is not (use cli)
    deletes are replicated
        delete of delete flag is not (so if you undo a delete that is not transferred)
        delete of older versions is not
    Can't replicate to multiple buckets yet

## Lifecycle mgmt .. versioning NOT needed
    S3 -> S3-IA -> glacier 
must be >128kb in size and 30 days (or more) after 
Can have delete rule after x time


## Data conistency
    REad after write for consistency for puts for new objects
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

IA Infrequent access. Pay for retrieval 
RRS Reduced Redundancy storage (is this IA 1 zone?)
    availability 99.99   Durability 99.99 (4 9s intead of 11)

Faster upload if you allow multipart files for big files.
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
    Connect via internate/ Direct Gatway of vpcs
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
	

