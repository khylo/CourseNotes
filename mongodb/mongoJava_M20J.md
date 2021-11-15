# Java course
https://university.mongodb.com/mercury/M220J/2021_July_27/chapter/Chapter_0_Introduction_and_Setup/lesson/5bef102b4feed9b6058263e1/lecture

## URI
mongodb+srv://<user>:<password>@<host>/<db>

Note, HOST does not represent an actual hostname.. use srv as uri as it is refers to a local file which contains the list of machines. THat way machines can change without needing to update the uri

e.g. mongodb+srv://brigitte:bardot@xyz-1234.srv.net/admin
Only thing we an say is username, password and target db

## Test DB
DB setup on atlas
Download mongo shell: THen run

>mongosh "mongodb+srv://mflix.zjmvn.mongodb.net/myFirstDatabase" --username dbuser



## Commands
```db.serverStatus()```
Lists all details. CAn find servers in cluster here

