# Intro to GKE

Link: https://www.coursera.org/learn/google-kubernetes-engine/lecture/PBsXZ/introduction

 ## GKE
 Google Kubernetes Engine runs on top of GCE (Google Compute Engine), which provies infrastructure to run the containers on.
 
 These run in Multi regions and Regions and  Zones
 
 Multi-Regions = US or Europe or Asia .. Different continents
 Region = Independent areas in the saame ocntinent (or multi region) e.g.  Europe-West2 (london).. Fast network conenctivity within a region .. < 1ms round trip latency (95% or traffic)
 Zones  = Datacenter in a regione.g. europe-west2-a, europe-west2-b, europe-west2-c
 
 
 *Compute engines reside in a particalr Zone *
 
 
 Resource Management lesson talks about fault tollerance over multiple zones
  https://www.coursera.org/learn/google-kubernetes-engine/lecture/A57Tp/resource-management 
  See for infomration : https://cloud.google.com/compute/docs/regions-zones
  ![Google Network Diagram](https://photos.app.goo.gl/S8BM4f37WjMeujeR8)
 
 **GKE NODE is zonal**
 
Edge Network of machines, wher it automatically directs you to the nearest edge node (with lowest latency)

Resources sit in projects (logical)
*Logical*
* Organization
IAM policies inherit downwards.. Organization -> folder -> project
* Folders, e.g. team A Team B
* Projects e/g/ projets per team
Billing accumulates at Project level

## Billing
Billing account linked to one or more projects. can setup billing subaccounts

### Tooling
* Busgets and Alerts
Define at billing account or prject level. Can call webhook to action stuff if alarm is set
* Billing Export
Export billing data to systems for analysis. e.g. BigQuery, or file export
* Reports
Allows 
* Quotas
Rate Quota e.g. GKE api allows 1000 (admin) requests per 100 seconds
Allocation Quota: 5 networks per project
Can change these by dealing with google support.


*Physically *
* Global : Http(s) load balancers / Virtual Private Cloud
* Regional: Datastore , Regional GKE cluster
* Zonal: Persistent disk, GKE node, Compute Engine instance

## Interacting with Google Cloud
4 ways
* Google Cloud Console
* Cloud SKD and cloud shell .. Run from within browser.
Ephermeral compute Engine .. Which starts up as you use it. 5 GB of storage.. Build in authorization for access to cloud consoel projects
Cloud shell code editor
* Cloud console mobile app
* REST API

## QwikLAbs
Login with incognito


