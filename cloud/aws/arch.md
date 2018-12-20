# Security

### SEcurity
#### Design Principles
Apply security on all layers . Firealls, NACLs, etc
Enable traceability
Automate responses to security events
Focus on securing your system
Automate security best practises
#### Definition
..* Data protection
Classify your data in segments e.g.g public, groups, etc.
Implement Least privililge.
Encrypt data at rest and in motion
* Privilege managment
Only authorized and authenticated users allows
ACLs
Roles based access password managment
* Infrastructure protection
How are yopu enforcing network and host level boundary
How are you enforcing AWS service level protection
How are you protecting OSs e.g.g virus protector.
* Detective controls
CloudTrail .. log ever change (per VPC/ region)
CloudWatch .. e.g. CPU or ram goes up
S3
glacier
#### Best Practises
#### Key Aws SErvices
#### REsources

 

# Reliability
Recover from outage, and growing to meet demand
* Test Recovery Procedures  e.g. Simian army
* Automatically recover from failure
* Scale horizontally to increase availability (e.g. ELB)
* Stop guessing capacity.  e.g. auto Scaling and ELB

## Reliability consists of 3 areas
Foundations, Change manamement, Failure management.
 
## Foundations
e.g. building house. Need the foundations.

e.g. size of pipe between data center and offices. It can take months to upgrade

AWS handles this for you within Service limits. These can be changed via ticket to account manager.

e.g. VPCs per region = 5 , subnets per VPC 200, Virtual Private gateways per region 5
 
How are you managing AWS service limits?
How are you planning network topology
Do you have an escalation path to deal with issues. Shoudl you upgrade your level (basic, developer, business, enterprise) to get better support.

 

* Aws services .. VPC, IAM

 

## Change Management

Need to be aware how change affects your sytem.

 

Monitoring allows you to detect changes

CloudTrail allows you  to have audit trail of changes

CloudWatch can look for changes in demand and notify, or autoscale.

How do you execute change management

## Failure Management
Always architect assuming failure will occur.

 * How are you backing up data
* How does you system withstand component failures
* How are you planning fo recovery
* AWS services. Cloud formation. RDS, multi AZ. Fail from primary to secondary

 


# Performance Efficiency

* Democratize advanced technologies.. e.g. machine learning, media transcoding.
using noSql. Can just deploy and use.
* Go global in minutes.
* Use serverless architectures.
* Experiment more often.

## 4 Areas
1. Compute
2. Storage
3. DataBase
4. Spave-time trade off

### Compute
Choose the right server. Is it more CPU or memory intensive, maybe a specialist server would suit better.
Can change server type and run benchmarks to see what is best.
Also consider serverless. (What about ECS? IS that on a EC2 instance? WHat about EKS. THat shoudln't be should it?)
Don'T forget to monitor and see that its working
What quantity of servers (e.g. autoscaling)

How do we keep track of what we are using (e.g. say a new compute engine comes out thats better than the one we use. How do we find all copies of olde one and update them all?)

### Storage
* WHat is the optimal storage mechanism. Based on these questions
* Access Method  e.g. Block/ File/ Object
* Pattern of access - e.g. Random or Sequential (block, e.g. DB)
* THroughput required .. e.g. for EBS could be Magnetic, SSD, provisioned IOPs (PIOPs)
* Frequency of Access (e.g. S3 or glacier, or S3 IA)
* Frequency or update e.g. Worm (write once) ,or dynamic 
* Availability constrainst  S3 
* Durability Constrainsts  S3 RRS

Then we need to ensure this is correct via monitoring, alerting (e.g.g EBS filling up)

### DataBase
* CAP. .which type of DB to use?
* RDS .. What DB do you want here
* DynamoDB .. 
* RedShift
* Netprun (graphDb)

Monitoring, and throughput.
e.g. using RDS can create read replicas for higher read throughput and reduce load on write DB.
Use Direct conenct to provide predicable latency between HQ and AWS.
Caching solutions for some data

### Space-time trade off
CloudFront  (dupliate data to increase throughput/ decrease latency)
ElastiCache (again suplicates to reduce DB calls)
Direct Connect
RDS Read Replicas
