# Overview
Taken from https://www.youtube.com/watch?v=rIcy-kbnbFk&t=331s&ab_channel=PaidCourses



## Cheat sheet

```
docker run   <name> #  made up of 'docker create <imageName>'  and 'docker start -a <id>' 
docker create
docker start
docker build -t <tagname>  .    # build from a dockerfile   tagname normally, dockerId/Repo/Project Name/Version  .. Tag as image name plus version (tag)
docker scan <image> # Scans for vulnerabilities


docker stop <container>  # Stop, then kill running container

docker system prune   # Kills and deletes stored images/ containers
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

## Sample building simpleWeb
See simpleweb. Note we used the latest bversion of node which requires adding a WROKDIR directive to DockerFile. See https://stackoverflow.com/questions/57534295/npm-err-tracker-idealtree-already-exists-while-creating-the-docker-image-for 
build with ```docker build -t khylo/simpleweb .```

```> docker scan khylo/simpleweb

Testing khylo/simpleweb...

✗ Low severity vulnerability found in apk-tools/apk-tools
  Description: CVE-2021-36159
  Info: https://snyk.io/vuln/SNYK-ALPINE313-APKTOOLS-1533754
  Introduced through: apk-tools/apk-tools@2.12.5-r0
  From: apk-tools/apk-tools@2.12.5-r0
  Fixed in: 2.12.6-r0
```

