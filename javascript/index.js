const express = require('express');
const { readFile, readFileSync } = require("fs");
const app = express();

/*
Old way with Callbacks
*/
app.get('/old', (req, resp) =>{
    readFile("./home.html", "utf8", (err,html) =>{
        if(err){
            console.log("Ugh oh error");
            console.log(err);
            resp.status(500).send("Out of Order!!");
        }
        console.log("Wahey, request");
        console.log(req);
        resp.send(html);
        console.log("Wahey, we is sending response!!");
    });
    console.log("Wahey, we are exiting main prog, but leaving event waiting for html read!!");
});

/*New way with Promises. First we imprort 
*/
app.get('/', async (req, resp) =>{
    console.log("Wahey, request");
    console.log(req);
    resp.send(await readFile("./home.html", "utf8"));
    console.log("Wahey, we is sending response!!");
});

app.listen(process.env.PORT || 3000, ()=> {
    console.log("Wahey, we is running, probably on port 3000, unless env.PORT is set!!");
});

process.on('exit', () => {
    console.log("Exiting.. Nice to have worked with you!")
})