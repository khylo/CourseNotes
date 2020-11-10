# Intro to GKE

Link: https://www.coursera.org/learn/google-kubernetes-engine/lecture/PBsXZ/introduction

 ## GKE
 Google Kubernetes Engine runs on top of GCE (Google Compute Engine), which provies infrastructure to run the containers on.
 
 These run in Multi regions and Regions and  Zones
 
 Multi-Regions = US or Europe or Asia .. Different continents
 Region = Independent areas in the saame ocntinent (or multi region) e.g.  Europe-West2 (london).. Fast network conenctivity within a region .. < 1ms round trip latency
 Zones  = Datacenter in a regione.g. europe-west2-a, europe-west2-b, europe-west2-c
 
 
 *Compute engines reside in a particalr Zone *
 
 
 Resource Management lesson talks about fault tollerance over multiple zones
  https://www.coursera.org/learn/google-kubernetes-engine/lecture/A57Tp/resource-management 
  See for infomration : https://cloud.google.com/compute/docs/regions-zones
  ![Google Network Diagram](https://photos.app.goo.gl/S8BM4f37WjMeujeR8)
 
