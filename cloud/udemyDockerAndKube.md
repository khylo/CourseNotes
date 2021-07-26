# Overview
Taken from https://www.youtube.com/watch?v=rIcy-kbnbFk&t=331s&ab_channel=PaidCourses



## Cheat sheet

```
docker run   <name> #  made up of 'docker create <imageName>'  and 'docker start -a <id>' 
docker create
docker start
docker build -t <tagname>  .    # build from a dockerfile   tagname normally, dockerId/Repo/Project Name/Version  .. Tag as image name plus version (tag)
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

alternatively, silly but possbile to do same with command line
```
docker run -it alpine sh    '' Run sh command on alpine image
## Inside docker
apk add --update redis   
docker ps  # Get imag ename
docker commit -c 'CMD ["redis-server"] <image name>
```
