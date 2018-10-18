# EC2
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
	..* EC2
	..* EC2 Container Service (ECS)
	..* Elastic Beanstalk (in developper associate exam)
	..* Lambda
	..* LightSail VPS service.. Easier th
	..* Batch  

## Host types
    Dr Mc GiftPx
    Density
    Ram
    Main choice
    Compute
    Graphics
    Iops
    FPGA
    T cheap main use e.g. t2
    Pix (Graphics)
    Xtreme Memory
now
    Fight Dr McPx
    High Disk Tru put

## EBS Elastic Block Storage
So EBS volume's placed in a specific availabilities zones where they are automatically replicated to protect
    Root volumne must be either SSD.. 2 types or magnetic standard
    	- General purpose ssd GP2 ... 3IOPs per gig => 3000 IOPS for volumes 3334GB and above
        -Provisioned IOPS SSd.. For high perf >10000 IOPS

    Magnetic types
        Magnetic Standard (previous generation )
        throughput optimized HDD.. big data, data warehouse. frequenctly acessed
        sc1.. cold HDD .. less frequent. lowest cost hdd

by default EBS deleted with Ec2 instance, but can change
Additional volumes can be encryptrd. By default root can't but can by either 3rd part apps or creating custom AMI can allow you to do it	

	### Raid
	Can use Raid 0 (striped) to increase IOPS of EBS volumes.
	In lad he created windows VM with 5 EBS volumes. THen login to windows. Delete 4 volumnes/ drives (leave root drive). Then right click in Disk 1 and select New Striped Volume, and add all diskss, and assign to single drive letter
	This now maps all drivees to 1 logical drive so can increase performance.
	
	..* How to take snapshot
	When taking snapshot of RAID array, caching can be a problem.
	Have to stop application from writing to disk, and flush all caches to disk.
	One of these 3, then take snapshot
	..* Freeze filesystem
	..* Unmount Raid Array
	..* Shutdown associated EC2 instance  (favourite)
	
### How to encrypt volumne.
		..* Create snapshot. (Best practise is to stop EC2 instance first)
		..* Copy snapshot to new region (this allows us to encrypt.)
		..* Create Image from snapshot
		..* Note can share encrypted volumes/ images with others (since they wont have key)

### SElect AMI based on (most root Volumes are EBS)
..* REgion
..* OS
..* Architecture (32 vs 64)
..* Launch Permissions
..* Storage for the Root Device (Root evice Volume)
			Instance Store (Ephemeral Storage).. Can't stop instance.. Can't detach volume. Can't add Instance Store volumnes after device is started . If uderlying host fails you will lose your data.
			EBS backed volumes. When deleteing Ec2 instance can choose not to dlete root volume,
			
	Cannot stop Instance Store instances
	Sometimes stop and start instance if you want o restart it on new Hypervisor (e.g. if issue with hypervisor) but can't with Ephemeral/ Instance Store.
	
### Elastic Load Balancer (ELB)
3 types
..*	Application Load Balancer  
	Best suited for http/ https
	Operate at layer (OSI) 7/ Application aware
	Intelligent and you can crate advanced request routing (depending on app / client )sending specific requests to specific web servers
..* Network Load Balancer
	Extreme performance. Level 4 (connection) Can handle millions of requests per second 
..* Classic Load Balancer (legacy ELB.  should use one of other 2)
	Does bit of both from above (uses sticky sessions and/ or X-Forwarded-For Header-for (contians public IP of sender) not sully application aware)
	
	Error 504 is a gateway  error which means that https server somewhere has failed, and LB can't communicate with it.
	
	LoadBalancers are access via DNs (IP not given , it is managed by AWS)
	Readt ELB faq for classic Load Balancers (for q's)

##CloudWatch

..* Dashboards
..* Alarms .. Set alarms when thresholds are met
..* Events .. Respond to events/ changes of state
..* Logs .. aggregate, store, moitor logs.. must install agent on device

CloudWatch for performance  monitoring
    e.g. setup event whenever ec2 state is changed to running and had t forwarded to lambda function (which just prints info)
CloudTrail is for auditing what is been done on Aws account
Note for traffic monitoring should maybe use VPC Flow logs

	
	### VPC
	VPCFlow logs is a feature that enables you to capture information about the IP traffic going to and from network interfaces. Flow log data is stored using CloudWatch Logs. Use it for monitoring IP traffic for instance.