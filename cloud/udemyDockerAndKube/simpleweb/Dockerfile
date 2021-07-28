# Updated version changes COPY into 2 steps.
# 1 Copy required package.json file for building app
# 2 npm install
# 3 copy other files
# 4/ This speeds up dev if we change index.js for example by using cached versions for every step up to last COPY. 
#
# docker build -t khylo/simpleweb .
# docker run -p 8001:8080 khylo/simpleweb
# docker run
# base image
FROM node:alpine

#Create variable 
ENV APP_DIR=/home/node/simpleweb
#Specify the working directory. USed in copy and run command. AVoid #6 1.018 npm ERR! Tracker "idealTree" already exists See https://stackoverflow.com/questions/57534295/npm-err-tracker-idealtree-already-exists-while-creating-the-docker-image-for
WORKDIR ${APP_DIR}
# Copy current working folder contents to image
COPY ./package.json ${APP_DIR}
#install dependencies
RUN npm install
COPY ./ ./

# Default command
CMD ["npm", "start"]