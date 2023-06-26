const { readFile, readFileSync } = require("fs").promises;

readFile("Node.md","UTF-8", (err,txt) => {
    console.log("This get prined second");
});

console.log("Welcome to app. THis gets printed first")
