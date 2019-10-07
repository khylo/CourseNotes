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

## Actions
console.actions.google.com
used interaction with google voice etc
linbked with firebase

uses Diallog flow

## DialogFlow
Natural language parser.Allows users to build intents and actions and link the actions to Google Functions

### Lab create Dialog flow intent. Parse with Google cloud founction
See https://google.qwiklabs.com/focuses/4784?parent=catalog

Update cloud function code
#### Function
'use strict';
// UPDATE variables on line 18 and 27
const {
  dialogflow,
  Image,
  Suggestions
} = require('actions-on-google');

const functions = require('firebase-functions');
const app = dialogflow({debug: true});

function getMeters(i) {
     return i*1609.344;
}

app.intent('get_restaurant', (conv, {location, proximity, cuisine}) => {
      const axios = require('axios');
      var api_key = "<YOUR_API_KEY_HERE>";
      var user_location = JSON.stringify(location["street-address"]);
      var user_proximity;
      if (proximity.unit == "mi") {
        user_proximity = JSON.stringify(getMeters(proximity.amount));
      } else {
        user_proximity = JSON.stringify(proximity.amount * 1000);
      }
      var loc = JSON.stringify(location);
      var geo_code = "https://maps.googleapis.com/maps/api/geocode/json?address=" + encodeURIComponent(user_location) + "&region=<YOUR_REGION>&key=" + api_key;
      return axios.get(geo_code)
        .then(response => {
          var places_information = response.data.results[0].geometry.location;
          var place_latitude = JSON.stringify(places_information.lat);
          var place_longitude = JSON.stringify(places_information.lng);
          var coordinates = [place_latitude, place_longitude];
          return coordinates;
      }).then(coordinates => {
        var lat = coordinates[0];
        var long = coordinates[1];
        var place_search = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + encodeURIComponent(cuisine) +"&inputtype=textquery&fields=photos,formatted_address,name,opening_hours,rating&locationbias=circle:" + user_proximity + "@" + lat + "," + long + "&key=" + api_key;
        return axios.get(place_search)
        .then(response => {
            var photo_reference = response.data.candidates[0].photos[0].photo_reference;
            var address = JSON.stringify(response.data.candidates[0].formatted_address);
            var name = JSON.stringify(response.data.candidates[0].name);
            var photo_request = 'https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=' + photo_reference + '&key=' + api_key;
            conv.ask(`Fetching your request...`);
            conv.ask(new Image({
                url: photo_request,
                alt: 'Restaurant photo',
              }))
            conv.close(`Okay, the restaurant name is ` + name + ` and the address is ` + address + `. The following photo uploaded from a Google Places user might whet your appetite!`);
        })
    })
});

exports.get_restaurant = functions.https.onRequest(app);
#### Package.json
{
  "name": "get_reviews",
  "description": "Get restaurant reviews.",
  "version": "0.0.1",
  "author": "Google Inc.",
  "engines": {
    "node": "8"
  },
  "dependencies": {
    "actions-on-google": "^2.0.0",
    "firebase-admin": "^4.2.1",
    "firebase-functions": "1.0.0",
    "axios": "0.16.2"
  }
}
