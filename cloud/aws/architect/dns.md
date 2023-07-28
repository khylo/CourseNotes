# DNS

Can we use multiple routing policies. e.g. geolocation (for regulation) goes to loadbalncer per region with multiValue answer to spread load?? Crazy? Why not just ELB instead?

 

ipv4 32 bit

 

ipv6 128 bit

 

Normally if creating policy must delete existing policy (so in lab we must delete weighted policy before creating latency based policy). THere are some exceptions to this

 

Zone Apex domain = naked domain = cnn.com (created with Alias)

 

Note testing with DNS's can be slow. Need to have caches expire locally and on local DNS server, so changes can take time to propogate even with low TTL

 

## TLDs

TLDs controlled by IANA   internet assigned numbers authority

 

## Domain REgistrars

                Authority that can assign domain names directly under one or more TLDs.

                These  domains are registered with InterNIC s service of ICANN which enforces uniqueness of domain names accross internet .. => whois db

 

## Route 53

There is a 50 domain name limit by default. But this can be raised by contacting AWS support
port 53 is DNS

 

### Start of Authority Record (SOA)

SOA record stores information about (including A recrod => Address record (ip address))

1. THe name of server that supplied the data for the zone

1. The administrator of the zone

1. THe current version of data file

1. The default TTL on resource records  (DNS change ttl) Normally want this low so that name changes get propogated fast. Default 300 s

 

### NS Name server records e.g. nslookup

NS = Name Server record. Used by TLD servers to direct traffic to the content DNS server which contains the authoritative DNS record

example Lookup address. e.g. hi.com

First ISP looks in its cache.

If not there goto .com registrar.

   .com record will have     DNS name, ttl (seconds), NS url  , ex. hello.com 1728000 IN NS ns.awsdns.com

                .com then goes to ns.awsdns.com to get record

                This then returns SOA

 

 

### Alias record, came out after CNames cos before couldn't handle naked domain names

Alias records are used to map resource record sets to ELBs, cloudfront distros or S3 buckets that are configured as websites

Alias records are like CNAME record in that they are mapped to another DNS name (A-Record)

(e.g. cnn.com www.cnn.com)

 

 

 

### CName

Can be used to resolve one domain to another  .. e.g. duplicate IP address (mobile.web  m.web etc) Don't have A-record for each entry they just point to 1 A record

Key diff with Alias is that CNAMES can't be used for 'naked domain names' e.g. www.cnn.com and cnn.com.. Must be A-Record or Alais

 

ELBs only expose DNS (not IP). Means IP can change but DNS stays the

 

### MX Reconds

Email

 

### PTR records

Reverse lookup to find out if you own address.

 
## Route 53
DNS is port 53
1. Amazon Route 53 to register new domains, 
1. transfer existing domains, 
1. route traffic for your domains to your AWS and external resources, 
1. and monitor the health of your resources

### Routing Policies
1. Simple Routing
*One* record with multiple ips... e.g. load balancing
returns in random order
Can't create 2nd A-record with same name

1. Weighted Routing
    e.g. 20% one way  80% other server
    Can add multiple A reords

1. Latency Routing
Allow you to route your traffic based on lowest network latency (i.e. which region gives fastest response time)

Create Latency Resource Record Set.

When a query comes in Route%3 selects the Latency Resource Record for the region that gives the user the lowest latency, and responds with that record set.
1. Failoveer 
Failing Routing is when you want active/passive set up, e.g.g main site in eu-west-1 secondary in ap-southeast2 (sydney)

Route53 will monitor health of primary site using healthCheck

1st create healthCheck. Can check via endpoint/ status of other health checks, or state of cloudWatch Alarm.

Canmonitor via IP or dns (for ELB)

Note number of health Checker regions, do the health check (guess that sees potential regional issues)

Set primary in one record set

Set secondary in  a 2nd record.

 

If primary goes down will automatically route to secondary (after healthcheck fails)

When primary comes back it will automatically route back there.

1. Geolocation
Choose where your traffic is sent from based on users geolocation. E.g.g send from eurpoe if in europe

Like latency but solely based on location. even if latency is slower
1. MultiValu Answer
simple allows multiple IPs but only one record set

MultiValueAnswer => Create one record for each resource and optionally assiate Route53 healthCheck with each record.

e.g. 12 webservers each with own IP. 12 multiValue records,

Route53 responds to dns queries with up to 8 records in response. Different 8 per request.

Client can then use one of the 8, e.g. if request fails (say due to cahced IP address going down)

Option: sounds good but what about


#### lAB CREATE SIMPLE Routing
Route53
Create Record Set
Type = type A (naked)
set ttl as low as possible
Put ips (not http) in values per line
takes about 5 mins
Note whe testing, browser will cache value and so always get ame server (different pc  might get different server)



 
