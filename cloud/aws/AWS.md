# High Level Overview
	## AWS Global Infrastructure
	..* REgions, AZs, CloudFront
	
	## Compute  **
	..* EC2
	..* EC2 Container Service (ECS)
	..* Elastic Beanstalk (in developper associate exam).. designed to run a developer's code on an infrastructure that is automatically provisioned to host that co
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
	..* Code Star .. Project managing code . .with CDN tool chain 
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

