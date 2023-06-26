const { readFile, readFileSync } = require("fs");

const txt = readFileSync("Node.md","UTF-8");

console.log(txt)
console.log("Do this!!!")