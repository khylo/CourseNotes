See https://www.youtube.com/watch?v=ENrzD9HAZK4&ab_channel=Fireship

```
@rem get version
node -v  

@rem REPL,
node  

# OR
# This will load index.js. Nore for these exampel index.js is used for the express examples below
node .   
# Or point to filename
node <filename>
```

Differences between node and browser

* console object to mimic console log in browser
* global . Like a global map object.
```
# See global.js for next few examples
console.log(global.myNum);
global.myNum='23';
console.log(global.myNum);
```
* process. Access to global process object, such as ```process.platform``` =  (win32 or linux), or ```process.env.PATH``` , process.on(<event>, callbaackFn) allows us to listen to events, e.g. process.on('exit') (See global.js)

Events
ASyncronous event driven runtime
Uses non blocking event loop, that reads from event queue calling blocking operations and running the callbacks when finished.
Can use process.on to listed for standard events
To create custom events see  eventEmitter (eventEmitter.js)

# Access to fs (filesystem)
Can be blocking or async
If command ends in Sync then it is blocking
for example we have see resadFile.js and reafFileAsnc.js
* readFile  # Passin callback to deal with file once read
* readFileSync    # Blocking

# Promises
Promises chould offer cleaner code than callbacks
See *readFilePromise.js* 

# Modules
Node has a number of modules . Bring them in with ```require()``` # original way to do it, or ES6 way, ```import / export```
require() vs import() Functions
The require and import functions/statements are used to include modules within your JavaScript file, but they possess some differences. The two major differences are: The require() function can be called from anywhere within the program, whereas import() cannot be called conditionally. It always runs at the beginning of the file.
To include a module with the require() function, that module must be saved with a .js extension instead of .mjs when the import() statement is used.

* events
* fs
* http
* https
* net
* os
* path
* querysting
* stream
* v8 js runtime

# Node
```
# Iniitalse a new node module. THis creates package.json
npm init -y
# Install minimal webserver
npm install express
# Check package.,jsoin now. express added to dependencies.
```

# Express
For these examples we use index.js

# Google App Engine
Firstly we need to install google cli
https://cloud.google.com/sdk/docs/install-sdk
Optionally createa project
>gcloud projects create node-tutorial-kh

Set project name
```gcloud config set project node-tutorial-kh```

Add app.yaml so appEngine knows what to run (here is node, See https://cloud.google.com/appengine/docs/standard/reference/app-yaml?tab=node.js#top)
We also need to add a start-script to package.json

So we add
```
"start": "node .",
```
run *gcloud app deploy*
