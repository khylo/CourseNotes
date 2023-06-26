//import { myModule } from './my-module'
const myModule = require("./my-module");
console.log(global.myNum);
global.myNum='23';
console.log(global.myNum);
console.log(process.platform);
console.log(process.env.PATH);

process.on('exit', () => {
    console.log("Exiting.. Nice to have worked with you!")
})

console.log(myModule);