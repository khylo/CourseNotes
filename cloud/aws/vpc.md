#VPC
A VPC spans all the Availability Zones in the region. After creating a VPC, you can add one or more subnets in each Availability Zone. When you create a subnet, you specify the CIDR block for the subnet
# VPC

 

virtual data center in the cloud

We create subnets (per AZ)

Configure route talbe between subnets

Each region has default VPC. (Each VPC has a region)

Create internet gateway and attach to VPC (only 1 internet gateway per VPC).

Internet gateways are by default spread across AZs so should be highly available.

Can crate publicly facing subnet for webservers and put backend systems in private subnet with no internet access.

Can use security Groups and NACLs (network Access control lists)

 

Security Groups can span subnets (AZ's .. not regons)

1 subnet spans 1 AZ

 

Can also create Hardware VPNS between corporate datacenter and your VPC.

 

DEfault VPC vs Custom

Default is user firendly per region

* All subnets have route to internet (public not private

* Each  Ec2 instance has both public and private IP address. (if private vpc then don't get public IP address)

 

#VPC Peering

Allow syou to connect mulktiple VPX via direct network route using private IP address.

Instances behave as if they were on same private networkYou can peer VPCs with other AWS accounts as well as with other VPCs

Peerin is in a star configuration. 1 central VPC peers with 4 others. NO TRANSITIVE PEERING. So must connect VPCs individually

 

 

##IP ranges

10.0.0.0 -> 10.255.255.255  (10/8 prefix) => 16 million

172.16.0.0 -> 172.31.255.255  (172.16/12 prefix) -> 1 million

192.168.0.0 -> 192.168.255.255  192.168/16 prefix -> 65k

 

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