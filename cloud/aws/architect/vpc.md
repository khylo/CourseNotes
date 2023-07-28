# VPC
A VPC spans all the Availability Zones in the region. After creating a VPC, you can add one or more subnets in each Availability Zone. When you create a subnet, you specify the CIDR block for the subnet
when adding all ips use 0.0.0.0/0 for all IP4, and ::/0 for all ip6



virtual data center in the cloud
We create VPCs pre region . By default allowed up to 5 per region.
We create subnets (per VPC/ per AZ)

Configure route talbe between subnets

Each region has default VPC. (Each VPC has a region)

Create internet gateway and attach to VPC (only 1 internet gateway per VPC).

Internet gateways are by default spread across AZs so should be highly available.

Can create publicly facing subnet for webservers and put backend systems in private subnet with no internet access.

Can use security Groups and NACLs (network Access control lists)

 

Security Groups can span subnets (AZ's .. not regons)

1 subnet spans 1 AZ

 

Can also create Hardware VPNS between corporate datacenter and your VPC.

 

DEfault VPC vs Custom

Default is user firendly per region

* All subnets have route to internet (public not private

* Each  Ec2 instance has both public and private IP address. (if private vpc then don't get public IP address)

 

# VPC Peering

Allow syou to connect multiple VPCs via direct network route using private IP address.

Instances behave as if they were on same private networkYou can peer VPCs with other AWS accounts as well as with other VPCs

Peerin is in a star configuration. 1 central VPC peers with 4 others. NO TRANSITIVE PEERING. So must connect VPCs individually

# NATs
## NAT Instance
Single server (created from community AMIs) that acts as a router between private subnets and public subnets (See lab for instructions)
Not good idea since by default single point of failure etc. Shoudl use NAT Gateway instead
THroughput depends on size of instance you create, e..g t2 micro => small, M4 better  but more expensive
## NAT Gateway (for ip4 only)
Highly available.. Implemeted with redundancy (in 1 AZ). Must create in multiple AZ's for zone redundancy
Higher bandwith than isntance
Maintained by AWS instead of you (no ec2 / amis etc)
Don't need security groups , again handled by AWS
Need to create them over multiple AZs
More secure since you don't even login to them
Does not support *Bastion server*, instance does
    A bastion host is a special purpose computer on a network specifically designed and configured to withstand attacks. The computer generally hosts a single application, for example a proxy server, and all other services are removed or limited to reduce the threat to the computer.
    A NAT (Network Address Translation) instance is, like an bastion host, an EC2 instance that lives in your public subnet. A NAT instance, however, allows your private instances outgoing connectivity to the internet while at the same time blocking inbound traffic from the internet.

### NAT vs Bastion 
    NAT is used to provide Internet access to a private subnet (sits in public subnet)
    Bastion (Jump box) is use dto provide secure access to administer EC2 instances in private subnets. Only need to harded Bastion server.. Other servers are only accessable via it.

    For EC2 in private subnet to send traffic out (e..g to S3, it must have route to get out via NAT, and a role to write to S3)
## EGresss Only Internet Gateway (for IP6)

## NACLs
* By default Aws create NACL for VPC. All subnets are by default associated with it.
* It allows all traffic in and all traffic out. (separate rules for IP4 and IP6)
* Note subnets can only be associated with one NACL.
* NACLs can be associated with many subnets
* NACLs can only be associated with 1 VPC (but multiple subnets) => NACLs can span AZs (like VPCs)
* NACLs define inbound and outbound rules separately (unlike security groups)
* Rules are incremented in numeric order
* NACLs kick in before security groups
* Each subnet MUST be associated with exactly one NACL. If none specified it will goto default (all open) NACL

 
If you create a NACLs by default it is DENY to all. (exact opposite to one that AWS creates by default)

Recommend as you add rules use rule #s in 100s

e.g. in example for inbound  (protocol tcp(6)?? Not sure what this is )

we add rule 100 for port 80 http, 200 for port 443 https , rule #300, port 22 SSH.. Deny all else

for outbound we allow port 80, port 443. Not port 22, but then we allow ephermeral ports 1024 -> 65535 so actualy streaming communication ports

## Security Group vs NACL
See https://www.infinitypp.com/amazon-aws/certified-solutions-architect/security-groups-vs-network-acls/ 
Security Groups supports only Allow rules.
Network ACL supports Allow and Deny rules.

Security Group is Stateful, any changes applied to an incoming rules is automatically applied to an outgoing rule.
Network ACL is Stateless changes applied to incoming will not be applied to Security Group.

Security groups are tied to an instance.
Network ACL are tied to the subnet.

Secuity group is the first layer of the defense.
Network ACL is the second layer of the defense.

Security group all rules are applied.
Network ACL rules are applied in order, with rules with lower number processed first.

If your planning to take the exam the difference between stateless and stateful is very important. This is a good post about the differences in detail

## Load Balancers with VPCs

Note when setting up ALB you must balance over 2 subnets in 2 different AZs, and both must be publicly facing

So in lab we currently can’t do this since only 1 subnet is publicly facing

## VPC Flow Logs
Capture information aobut the IP traffic going to and from network
Stored using CloudWatch logs

Can be at VPC level
Subnet level
At network interface level.
Not all IP traffic is monitored. Not monitored. 
    DNS, 
    windows license activation, 
    trafiic to and from 169.254.169.254 for instanse metadata,
    DHCP traffic
    traffic to the reserved IP address for default VPC router

## VPC Endpoints
Another way for private hosts to get access to say AWS services is to use a VPC endpoint.
e.g in lab we delteed route from private subnet to NAT so "aws s3 ls " failed.
To fix we created an Endpoint (unde VPC) to S3 Gateway
Endpoints can be Interface (single instance) or gateway (like NAT gateway, highly avaialbe.)
Note when you create make sure no existing connections as they may get dropepd.


 # Lab
 We created a VPC 10.0.0.0/16 with 2 subnets  10.0.1.0/24  and 10.0.2.0/24
 MAde one publicly available via internet gateway.
 Created EC2 in each.
 Made the other the subnet for DB, and gave it security group with  access to SSH/ RDS/ HTTP/ HTTPS/ ICMP (ping) from 10.0.1.0/24

 ## Nat Instances and NAT Gateways
 (Not good solution. Add NAT instance to allow private instances get out to internet. Bottleneck, single point of failure, single AZ. Could do autscaling etc but gets compliated.. Should use NAT gateway instead.)
 NAT instance from EC2 community. 
    Create AMI instance. Add to VPC and *public* subnet
    Add to myWebDMZ
    Disable Source/Destination Check ->  Actions/ Networking/ Change Source/ Dest Check.. Disable (Means that instance does not have to be source or destination of traffic, which is checked by default)
    Goto VPC, add route out of private subnet to NAT. (destination anywhere 0.0.0.0/0) to target of Nat Instance

## NAT Gateway (for ip4 only)
Add to public subnet.
Create Elastic IP and add.
Add route 0.0.0.0/0 to NAT gateway

Question 5:
You have a VPC with both public and private subnets. You have 3 EC2 instances that have been deployed in to the public subnet and each has internet access. You deploy a 4th instance using the same AMI and this instance does not have internet access. What could be the cause of this?

ANS: THe instance needs either an Elastic IP address or Public IP address assigned to it. ??
 

## IP ranges

10.0.0.0 -> 10.255.255.255  (10/8 prefix) => 16 million

172.16.0.0 -> 172.31.255.255  (172.16/12 prefix) -> 1 million

192.168.0.0 -> 192.168.255.255  192.168/16 prefix -> 65k

When creating subnet in AWS It reserves som eIPs

5 not available... F1rst 4 Ips  (0,1,2,3) and last !P
0 is NEtwork address
1 reserved for VPC router
2 reserved by AWS for DNS
3 for futre use
255 broadcase, not used by AWS so removed.
 

https://www.nybi.org/subnet-2l.php

## Question

IP Address: 46.246.52.48 / 20

What is

Network Address 

1st IP is NetworkAddress +1

Last ip is Broadcast adderss - 1.

Broadcast Address

Subnet Mask 

 

Ans:

IP address = A.B.C.D

first 20 bits are network so .. 12 bits are left => 4096     16.255

So 12 bit => 4 bits (16) of C + all 8 bits (255) of D

So in jumps of 16 we see lowest address for C (that spans 52) is 48

 

Network Address  46.246.48.0 

Broadcast Address  46.246.63.255

Subnet Mask  255.255.240.0  (240  = 256 - 16)

 

## Question

What are the network address, broadcast address, and the subnet mask for a host with the IP Address below?

IP Address: 211. 52. 89. 210/ 28

so 28 bits are network so that leaves 4 bits for devices

So Network can hold up to 16 ips.

So A.B.C.D .. only D changes

So each network jumps up in 16's over D ... So to work out D.

210 % 16 =  2 => 208 is highest network range

Network = 211.52.89.208

Broadcast = 211.52.89.223 (= max before rolling over to next 16)

Mask = 255.255.255.240

 

## Q. What are the network address, broadcast address, and the subnet mask for a host with the IP Address below?

IP Address: 159. 204. 216. 198/ 28

D = 198 % 16 = 6 => D = 192

Network Address  159.204.216.192

Broadcast Address 159.204.216.207

Subnet Mask   255.255.255.240

 

IP Address: 195. 192. 251. 43/ 25 

7 bits  = 128

Network Address  195. 192. 251. 0

Broadcast Address  195. 192. 251.127

Subnet Mask   255.255.255.128