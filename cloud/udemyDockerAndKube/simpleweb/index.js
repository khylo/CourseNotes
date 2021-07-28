const express = require("express");
const port = 8080
const app = express();


app.get('/', (req,res) => {    
    var datetime = new Date();
    res.send('Hi there from Node/Express at '+datetime);
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

app.listen(port, () => {
    console.log("Server started on port "+port);
})