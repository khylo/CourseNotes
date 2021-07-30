const express = require("express");
const redis = require("redis");
const port = 8081
const app = express();
const redisClient = redis.createClient();
redisClient.set('visits',0);

app.get('/', (req,res) => {
    redisClient.get('visits', (err, visits) => {
        res.send('Number of visits is '+visits);
        client.set('visits', parseInt(visits)+1);
        var datetime = new Date();
        var user = {
            agent: req.header('user-agent'), // User Agent we get from headers
            referrer: req.header('referrer'), //  Likewise for referrer
            ip: req.header('x-forwarded-for') || req.connection.remoteAddress, // Get IP - allow for proxy
            screen: { // Get screen info that we passed in url post data
            width: req.params['width'],
            height: req.params['height']
            }
        };
        console.log(datetime+": Serverd page to "+JSON.stringify(user, null, 4));
    });
});
    
app.listen(port, () => {
    console.log("Server started on port "+port);
})