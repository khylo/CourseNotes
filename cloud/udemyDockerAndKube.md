# Overview
Taken from https://www.youtube.com/watch?v=rIcy-kbnbFk&t=331s&ab_channel=PaidCourses



## Cheat sheet

```
docker run   <name> #  made up of 'docker create <imageName>'  and 'docker start -a <id>' 
docker create
docker start
docker build -t <tagname>  .    # build from a dockerfile   tagname normally, dockerId/Repo/Project Name/Version  .. Fives image name
```
  
  
## Sample Dockjerfile
https://youtu.be/rIcy-kbnbFk?t=7690
```
  # Use alpine base
  FROM alpine
  # Download an dinstall dependencies. Note gcc is not needed just for example
  RUN apk add --update gcc
  RUN apk add --update redis
  #Tell imnage what to do wgen it starts as a container
  CMD ["redis-server"]
```
