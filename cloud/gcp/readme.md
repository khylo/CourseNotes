## StackDriver
Stackdriver Monitoring provides visibility into the performance, uptime, and overall health of cloud-powered applications. Stackdriver collects metrics, events, and metadata from Google Cloud Platform, Amazon Web Services, hosted uptime probes, application instrumentation, and a variety of common application components including Cassandra, Nginx, Apache Web Server, Elasticsearch, and many others. 

## GCF (Google Cloud Functions
Serverless funtions

## Gcloud console
Click on start cloud console .

### Commands

* gcloud
https://cloud.google.com/sdk/gcloud
example Create GCF 
gcloud functions deploy helloWorld \
  --stage-bucket [BUCKET_NAME-WITHOUT gs://] \
  --trigger-topic hello_world \
  --runtime nodejs6

* gsutil (Crud storage buckets)
e.g.g make bucket
gsutil mb -p [PROJECT_ID] gs://[BUCKET_NAME]


gcloud functions describe helloWorld


## AppEngine

Use mvn commands to deploy etc.
See https://google.qwiklabs.com/focuses/951?parent=catalog
Code: https://github.com/GoogleCloudPlatform/getting-started-java/blob/master/pom.xml
