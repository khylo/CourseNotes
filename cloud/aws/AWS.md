# High Level Overview

## AWS Cloud Best practises
### Benefits
REduced up front fees (captial)
Just in time infrastructure
Pay for what you use (usage based costing)
reduced time to market
automation - Scriptable infrastructure
Auto scaling
Pro-active scaling
Disaster recovery and Business continuity

### Design for failure
Assume fialure will occur. Desgin for this.

### Decouple you components
#### SQS Simple Queue service (Pull based as opposed to SNS)
First public AWS service.

*SQS always pull based system*

If you want push then Simple Notification Service (SNS)

When a consumer client reads a message, message gets marked invisible (1st phase) so other don’t pick it up. When job finishes it is deleted. *Thus, the consumer must delete the message from the queue after receiving and processing it.* If job fails (e.g. consumer dies) then message will timeout and appear again in queue
Visibility timeout is the amount of time that the message is invisible in SQS queue after a reader picks up the message. If job is not processed in this time message will become visible again and another reader will process it. Default = 30 seconds. Max is 12 hours. So jobs that take longer should not use  this.
Messges can contain up to 256KB of text .
Messages can stay in queue from 1 minute to 14 days. Default retention is 4 days
Could use monitoring to check size of queue and grow # consumers.

* Dead letter Queues. Amazon SQS supports dead-letter queues, which other queues can target for messages that can't be processed (consumed) successfully. Dead-letter queues are useful for debugging your application or messaging system because they let you isolate problematic messages to determine why their processing doesn't succee

##### 2 types
* Standard Queues . Guarantee message is delivered AT LEAST once. Can deliver more than one. Messages are generally delivered in order they are added to queue. But not guaranteed *High throughput*

* FIFO Queues. Guarantee messages delivered in order they arrive, and delivered only once> *limit of 300 transactions per second.*

Sql Long Polling.. waits (up to a timeout) for an item to appear in queue. Don’t need to keep polling over and over (default -> short polling)
1 million free per month then.
40 c per 1million requests standard
50 c per 1million requests fifo
plus egress pricing (for over 1GB) 

#### SWF -> Simple Workflow service
SWF stores tasks and assigns them to workers when they are ready and monitors their progress. It ensures a task is assigned only once and never duplicated.
Workers and Deciders run on cloud infrastructure like EC2 or on machines behind firewalls. SWF brokers bewtween workers and deciders. SWF maintains state so workers and deciders don't have to.
SWF Actors
* Workers.
Can start a workflow. Interact with SWF to get tasks, process them and return results
* Deciders
Split flow, ordering, scheduling
* Domain
Is a set of items for a given problem.

*Note tasks never repeated.*
With SQS they can be. Even with FIFO if message visibility exceeded.

SQS has retention of 14 days. SWF can be up to 1 year

#### SNS  (Push based as opposed to SQS). Under Mobile Services
Deliver notifications to
* SMS
* Email/ JSON  (SES Simple Email Server)
* any HTTP endpoint
* SQS
* Lambda

Group multiple recipients using Topics.
All messages in SNS are stored redundanly accross multiple AZs to stop message loss
E.g. in lab create email SNS. Emails gets sent to address to confirm subscription to message topic. Subscription is marked as pending until confirmed.

	50c per 1 million SNS requests
	then after
	6c per 100k notification delivieries over http.. Why not have a http service that sends emails?
	75c per 100 notiifcation over SMS   so .75c per message
	$2 per 100k over email

### Implement Elasticity (Back to cloud best practises)
Proactive cyclic scaling for known peaks, e.g. month end
Proactive event based scaling e.g. Christmas, black friday
Auto scaling based on demand (e.g. CPU utilization)




## AWS Global Infrastructure
*  REgions, AZs, CloudFront

## Compute  **
*  EC2  (see metaData)
*  EC2 Container Service (ECS)
*  Elastic Beanstalk (in developper associate exam).. designed to run a developer's code on an infrastructure that is automatically provisioned to host that co
*  Lambda
*  LightSail VPS service.. Easier th
*  Batch  

## Storage  **
*  S3 

## Databases **
*  RDS (aurora/ mysql etc)
*  DynamoDB (noSql)
*  Elasticache
*  Red Shift ... Data warehouse

## Migration **
*  Aws Migration Hub
*  Application Discovery Service (tracking depndency for apps)
*  Database Migration Service (migration in house DBs to AWS)
*  Server Migration Service
*  Snowball

## Network and Content Delivery **
*  VPC
*  CloudFront  .. CDN
*  Route 53  .. DNS
*  API Gateway (mmore on developer) Create api for other serives to talk to
    API caching. Reduce calls to endpoint. Results can be cached and used instead of calling endpoint eachtime.
	auto scaling
	Can throttle to prevent atacks
	CORS cross origina Resource sharing .. might need to be turned off, if using javascript and differnt domains are used
*  Direct Connect (connet to AWS)

## Devloper Tools
*  Code Star .. Project managing code . .with CDN tool chain 
*  Code Commit .. like Github
*  Code Build  builds
*  Code Deploy .. deploys to Ec2 or in house
*  Code pipeline
*  XRay. .analyse serverless 
*  Cloud 9.. IDE env so you can build directly into cloud

## Management Tool **
*  CloudWatch   monitoring 
*  CloudFormation   scripting infrastructure into code like firewalls 
*  CloudTrail logging / auditing changes to AWS environment (1 week by default)
*  Comnfig .. Keeps snapshots of AWS env and view
*  OpsWorks. .Uses chef and puppet to manage env
*  Service Catalog .. Managing catalog of serivces approved for use use d for governance	
*  Systems Manager .. Patch maintenance accross servers .. Group all resources by dept etc.
*  Trusted Advisor .. gives advice e.g. ports open..
*  Managed Services .. Will look after Ec2 etc.

## Media Services
*  Elastic Transcoder ... media trnsforming
	media transcoder. Covert between formats Pay per minutes for . Note you can select target (e.g. iphone, ipad) and it will know what format to goto
*  Media Convert .. Video transcoding 
*  Media Live .. Live video processing service
*  Media Package
*  Media Store
*  Media Tailor

## Machine Learning 
*   SageMake .. Deep learning (neural nets)
*   Comprehend .. Sentiment analysis
*   Deep Lens  .. Camera with smarts e.g.g face recognition
*   Lex .. Alexa  AI chatbot
*   Machine learning e.g. regression, e.g. recommendation
*   Polly .. takes text and turns into speech
*   Rekognition.. upload image/video. .It recognises items in media .. pe
*   Amazon Translate
*   Transcribe .. Automatic speech reconition.. Speech to text

## Analytics **
*   Athena  run sql queries against S3   serverless
*   EMR Elastic Map Reduce
*   CloudSearch
*   Elastic Search
*  Kinesis .. used in big data. .Injest large amount of data into AWS e.g. tweets
  e.g.  geospacial data like uber, social network data, game data, stock prices etc.
    Kineseis streams . Stored day in  numnber of shards (default 24hr up to 7 days) goto consumers (EC2) then onto output. 
	Kineseis firehose. No shards. more automated than streams. data analysed immediatly e.g.g with Lambda, then to - S3 then to RedShift.
	Kineseis Analytics.. run sql queries against data in streams or firehose
*  Kinesis Video Stream
*  QuickSight  Business Intelligence tool.. 
*  Data Pipeline. Move data between AWS service
*  Glue .. ETL  .. maybe middware Soa?

## Security Identity and Compliance **
*  IAM
*  Cognito   device authentication e.g. mobile apps, facebook, gmail , etc, use cognito to reguest temproary access
*  GuardDuty. Monitors for amlicious activity on AWS
*  Inspector. .Agent that runs and checks for vulnerabilities.
*  Macie. Scan S3 buckets and look for PPI (personally Identifieble Information)
*  Certificate Manage SSL certs
*  CloudHSM Hardware security Modules.. store keys
*  Directory Service. .Integrate Active Directory into IAM
*  WAF Web access firewall. XXS, check for amicioul activity
*  Shield   DDoS .. also shield advance 
*  Artifact.. .For audit and compliance.. docs from amaozon

## Mobile Serices
*  Mobile Hub
*  Pinpoint. .push notificatoins to mobile users e.g. 
*  App Sync.. Updates data in web and mobile apps
*  Device Farm.. test on different devices
*  Mobile Analytics
* 

## AR/ VR   Augment Reality.. Virtual Reality .. 
*  Sumerian, tool and language for 3d ar vr modelling

## Application Integration **
*  Step Functions
*  Amazon MQ
*  SNS .. Notificaition service	 
*  SQS .. Queue service 
*  SWF .. Simple Workslow service.. used by amazon shop floor.  ACtors in SWF are . .Workers, deciders (split, or decide on next step), starters (e.g. sources of data, like lambda, ecommerce site)

## Customer Engagement
*  Connect .. Call center in cloud. 
*  Simple Email Service.. Bulk emails

## Business Productivity
*  Alexa for Business, e.g. book meeting room, talk to IT
*  Chime .. Video conferencing
*  Work Docs.. dropbox for AWS
*  Workmail.. like gmail
* AWS ORganizations. Account Managment service to consolidate multiple AWS accounts into an organization and manage. Link all acounts to paying account for consolidated billing. Also has Organization units (like folders). Current limit of 20, but can add more.. Don't deploy to paying account
Can save money e.g.g with S3. Price goes down for more data. When all combined we might get lower rate.
E.g.g Also reserved accounts. Differnt teams can use spare reserved account of other groups
Note cloudTrail has to be setup on an account level.
But can have cloudTRail write to central S3 bucket (in paying acocunt) but enabling cross account access

## Desktop and App Streaming **
*  Workspaces  .. Virtual Desktop
*  App Streaming .. streaming application. .Running in cloud but streamed to device.. Like citrix.

## IOT
*  IOT Device Management, including machine learning
*  FreeRTOS.. Free OS for microcontrollers
*  Greengrass 

## Games
*  GameLift.
	
	

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

## Security Token Service (STS)
Grants users limited and temporary access to AWS resources. Users can come from 3 sources
1. Federation (joining users from one domain to another.. with help from Idenetity Broker)
* SAML (Security Assertion Marrkup language)  for authenticating with Active directory among others
* active directory credentials (does not have ot be IAM user)
* sso users without IAM credentials
1. Federation with Mobile apps 
* e.g.gfacebook/ google / openId
1. Cross Account Access
* Lets users from one AWS access resources of another.

*Question *
You are hostin gcompany website on EC2. Users of website must login using active directory credentials and get access to only their S3 bucket.
So must mix on site AD server with AWS
See diagram in section 12, lecture 111. WE write Identity broker.
*Identity broker talks to Ldap directory first, then STS, then back to app.*
Get 4 bits of information back from STS
Access key
Secret access key
token
duration
App uses these 4 pieces to query from S3.


## Workspaces
CAn use with client application. *Does not need IAM*

WIndows 7 by default
get local admin access
Workspaes are perisistent (get EBS volume)
All data on *D drive (not C)* is backed up every 12 hours


