# EC2
(termination protection off by default)
Types (pricing)
    on demand   per hour or per second depending on 
        short term spike
    RI Reserved i=Instances reserved for 1 to 3 years
        Applications that require steady state predictable cpu resources
        upto 75% off if you pay in front for 3 years
        Scheduled RIs e.g. for planned bigger load days like month end  upcoming sales etc.
    Spot 
        Flexible start and end times.. Batch jobs
        if  aws terminate instance (cos price > threshold) then don't pay of fractino of hour youve used.
        If you terminate you Do pay for fraction of hour
    Dedicated hosts
        Useful for regulatory requirements that don't allow multi tenant
		
## Sample of services
*  EC2
*  EC2 Container Service (ECS)
*  Elastic Beanstalk (in developper associate exam).. Paas
*  Lambda
*  LightSail VPS service.. Easier than manually
*  Batch  

## Host types
* Dr Mc GiftPx
* Dense storage fileservers/ data warehouse/ hadoop
* Ram
* Main choice
* Compute CPU intensive apps
* Graphics intensive e.g. video encoding
* Iops
* FPGA   financial transaction/ realtime graphics processing
* T cheap main use e.g. t2
* Pix (GPUs)
* Xtreme Memory  Spark etc (how is it different from R?)
now
** Fight Dr McPx **
* High Disk Tru put

	
	
## EBS Elastic Block Storage
So EBS volume's placed in a specific availabilities zones where they are automatically replicated to protect
    Root volumne must be either SSD.. 2 types or magnetic standard
    * General purpose ssd GP2 ...Up to 10000 IOPs 3IOPs per gig => 3000 IOPS for volumes 3334GB and above
    * Provisioned IOPS SSd.. For high perf >10000 IOPS

    Magnetic types
        Magnetic Standard (previous generation ).. Can be root /bootable 
        throughput optimized HDD.. big data, data warehouse. frequenctly acessed  (not bootable).. Min size 500GB
        sc1.. cold HDD .. less frequent. lowest cost hdd   (not bootable)
		HDD volumes are optimized for large streaming throughput (measured in MiB/S) is a better perf measure than IOPs
		

by default EBS deleted with Ec2 instance, but can change.
By default root volumne not encrypted, 
	Can encrypt it but must jump  through some hoops.
		By default root can't but can by either 3rd part apps or creating snapshot. Then deploy snapshot and click encrypt volume custom AMI can . Or can use 3rd party tools. 
Additional volumes can be encryptrd. 

by default EBS deleted with Ec2 instance, but can change
Additional volumes can be encryptrd. By default root can't but can by either 3rd part apps or creating custom AMI can allow you to do it	
EBS types
Volumes exist on EBS. EBS volumes are stored per AZ. When attaching to EC2 both ec2 and sbs must be in same AZ
Snapshots exist on S3 (incremental)
encrypted volumnes produce encrypted snapshots and they in turn produce encrypted volumnes if created as AMI.
You can share snapshots but only non-encrypted

For AMIs to be created you need to keep EBS snapshot of the root device

Ec2. .termination protecteion is off by default, ebs isdeleted
EBS root can be encrypted
volumes are ebs
snapshots are stored in S3.. snapshots are incremental


### Raid
	Can use Raid 0 (striped) to increase IOPS of EBS volumes.
	In lab he created windows VM with 5 EBS volumes. THen login to windows. Delete 4 volumnes/ drives (leave root drive). Then right click in Disk 1 and select New Striped Volume, and add all disks, and assign to single drive letter
	This now maps all drivees to 1 logical drive so can increase performance.
	
	*  How to take snapshot
	When taking snapshot of RAID array, caching can be a problem.
	Have to stop application from writing to disk, and flush all caches to disk.
	One of these 3, then take snapshot
	*  Freeze filesystem
	*  Unmount Raid Array
	*  Shutdown associated EC2 instance  (favourite)
	
### How to encrypt volumne.
*  Create snapshot. (Best practise is to stop EC2 instance first)	
*  Copy snapshot to new region (*this allows us to encrypt.*)
*  Create Image from snapshot (AMI)
*  Note can't share encrypted volumes/ images with others (since they wont have key)

### Select AMI based on (most root Volumes are EBS)
*  Region (Amis can only be launched from the region where they are stored, but can copy amis to other regions.)
*  OS
*  Architecture (32 vs 64)
*  Launch Permissions
*  Storage for the Root Device (Root device Volume)
	Instance Store (Ephemeral Storage).. Can't stop instance.. Can't detach volume. Can't add Instance Store volumnes after device is started . 
	*If uderlying host fails/ stops you will lose your data.*
			EBS backed volumes. When deleteing Ec2 instance can choose not to delete root volume,
			
	Cannot stop Instance Store instances
	Sometimes stop and start instance if you want o restart it on new Hypervisor (e.g. if issue with hypervisor) but can't with Ephemeral/ Instance Store.
	
### Elastic Load Balancer (ELB)
3 types
1. Application Load Balancer  
	Best suited for http/ https
	Operate at layer (OSI) 7/ Application aware
	Intelligent and you can crate advanced request routing (depending on app / client )sending specific requests to specific web servers
1. Network Load Balancer
	Extreme performance. Level 4 (connection) Can handle millions of requests per second 
1.  Classic Load Balancer (legacy ELB.  should use one of other 2)
	Does bit of both from above (uses sticky sessions and/ or X-Forwarded-For Header-for (contians public IP of sender) not sully application aware)
	
	Error 504 is a gateway  error which means that https server somewhere has failed, and LB can't communicate with it.
	
	LoadBalancers are access via DNS name (IP not given , it is managed by AWS)
	*Read ELB faq for classic Load Balancers (for q's)*
	
	When Setting up LoadBalancer
	We assign it accross Aailability zones (subnets) . but it is only per region. Note our example had LB spanning 3 az's but target was only in one.
	We give it a security group (normally webDMZ ) to open ports
	We assign listeners, which is what is listens on e.g. port 80
	We assign Target groups, as in what it forwards to, which can be added by instance name or ip
		In the target we set things like healthy threshold (how many sucess pings before its declared healthy), unhealth thrshld , timeout, interval, success codes.

## CloudWatch

*  Dashboards
*  Alarms .. Set alarms when thresholds are met
*  Events .. Respond to events/ changes of state
*  Logs .. aggregate, store, moitor logs.. must install agent on device

CloudWatch for performance  monitoring
    e.g. setup event whenever ec2 state is changed to running and had it forwarded to lambda function (which just prints info)
CloudTrail is for auditing what is been done on Aws account
Note for traffic monitoring should maybe use VPC Flow logs


## Launch Configuraiton and AutoScaling
  Like Elastic LoadBalancer it can monitor for healthCheck page
  
  When creating AutoScaling Group must 1st create Launch configuration (like ec2). This is the instance the autscaling group will launch. Note cannot edit launch instance config, must create new one if changing
  Can edit AutoScale config
  Add policy for when auto scling/ shrinking should occur.
  
  Attach ELB, so we can balance accross all instances. This will also check for failures and not route if instance is down.
  
  
  Then create autscaling group accross AZ (not region).  Ec2 autoscale group is assigned per Network vpc (which can only span since region).. Subnets can only span single AZ
  If it sees instances going down it will recreate them, and loadbalancer will only route to up instanecs
  Deleting Autoscaling group will delete all instances also
  
  A VPC spans all the Availability Zones in the region. After creating a VPC, you can add one or more subnets in each Availability Zone. When you create a subnet, you specify the CIDR block for the subnet

  Can also use launch templates now instead of lauch configuration
 
## EC2 Placement group (makes sense only for AutoScaling surely? )
 *  Custered placement groups
	Grouping of instances within a single AZ. PLacement groups are recommended for apps that need low latency e.g. cassandra, and big data cluster
	Only certain instances can be launched into a Clustered Placement Group.
		Cant do t2 micro or nano... Compute Optimized, Gpu, Memory Optimized, Storage Optimized
 *  Spread Placement Group(Newer)
	Group of instances that are placed on distict underlying hardware (i.e. not sharing host). Can spread AZs
	Recommended for apps that have a small number of critical instances that shoudl always be kept away from each other.
	
	AWS recommend homogonous instances within placement group (i.e. same instance type)
	You can't merge placement groupsYou can;t move existin ginstance into a placement group. (Need to create an AMI from instance and launch it that way)
  
  ## EFS   (NFS v4)
  File service
  Only pay for storage you No preprovisionily
  Data is stored accroos multiple AZs withinn a region. Can siupport 000's o concurrent connections
  REad after write consistency
  upto petabytes
  
  Chooises for 
  *  Performance Mode
	Default, or MaxIO if 100's or thoughsands of clients connecting to it
## Instance Meta Data . .get information e.g. public IP
curl http://169.254.169.254/latest/meta-data/
curl http://169.254.169.254/latest/user-data/

B.B. Must include last '/' ohtwise nothing returned
	
*  THroughput Mode
    Bursting (normal)
	Proviioned ..  Use Provisioned throughput mode for applications that require more throughput than allowed by Bursting 


## Lambda
can use API gateway to http requests
languages: Node.js, Java, C#, Go and Python

Charge per request and duration (per GB second) (emory used by time)
1st million requests free.. .20c per million after that
max time increased from 5 mins to 15 mins

no servers
continuous scaling (better than autoscaling).. scales out automatically
serverless, s3, dynamoDB
can trigger other lamdba functions

Can get compicated to debug, so can use XRay to help debug

### Lab
Create serverless website with Route 53, S3, Api Gteway lambda

### Triggers
* 	API Gateway
*  IOT
*  Alexa skills kit
*  Alexa Smart Home
*  CloudFront
*  Cloudwatch events
*  Cloudwatch logs
*  Cognito
* Kinesis
*  S3
*  SNS
*  CodeCommit*

## ec2 Summary
### Diffrent Pricing models
*  On Demand by secondc
*  spot
*  REserved
*  Dedicatd hosts/ ;icensing or tenent doesn't want to share

### Instnce types
Fight DrMcPx

FPGA
IOPS high speed storage,for dbs etc
Graphics
High disk throughput .. map reduce , hdfs etc
t cheap
D Dense storage fileservers/  hadopo
Ram  memory optimized
M mid
Compute  cpu intensice
p  GPU
Xtreme memory   .. Spark, 	


## VPC
	VPCFlow logs is a feature that enables you to capture information about the IP traffic going to and from network interfaces. Flow log data is stored using CloudWatch Logs. Use it for monitoring IP traffic for instance.
	
	
