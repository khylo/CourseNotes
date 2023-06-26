const { readFile, readFileSync } = require("fs");

readFile("Node.md","UTF-8", (err,txt) => {
    console.log("Thus get prined second");
});

console.log("Welcome to app. THis gets printed first")
