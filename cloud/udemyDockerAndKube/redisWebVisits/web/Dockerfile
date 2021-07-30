# Uses Docker compse.
# Redis web. .Note must run docker run redis in background
# docker build -t khylo/simpleweb .
# docker run -p 8001:8080 khylo/simpleweb
# docker run
# base image
FROM node:alpine

#Create variable 
ENV APP_DIR=/var/web
#Specify the working directory. USed in copy and run command. AVoid #6 1.018 npm ERR! Tracker "idealTree" already exists See https://stackoverflow.com/questions/57534295/npm-err-tracker-idealtree-already-exists-while-creating-the-docker-image-for
WORKDIR ${APP_DIR}
# Copy current working folder contents to image
COPY ./package.json ${APP_DIR}
#install dependencies
RUN npm install
COPY ./ ./

# Default command
CMD ["npm", "start"]