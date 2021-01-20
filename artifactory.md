# Artifactory advanced administration. 

Based on course at jfrog academy https://academy.jfrog.com/jfrog-artifactory-advanced-administration-2020 jfrog-artifactory-advanced-administration-2020 

$ARTIFACTORY_HOME/etc/binarystore.xml configures the shards

So filesystem is standars, but can also have s3. However different types do not necessarily work well together

```xml
<chain template="file-system"/>   <!-- Store files in the file system -->
<chain template="cache-fs"/>      <!-- file system  caching -->
<chain template="blob"/>          <!-- Stores blobs -->
<chain template="eventual"/>      <!-- Initially in cache, then to persistent -->
<chain template="retry"/>         <!-- TRies again after a fail until stored -->
<chain template="s3"/>            <!-- S3 compliant on Jet S3-t framework -->
<chain template="s3Old"/>         <!-- S3 compliant on J-clouds framework -->
<chain template="google-storage"/><!-- Google Cloud storage -->
<chain template="sharding"/>      <!-- sharded file storage, user configures mounts (number of shards) and redundancy (number of copies each file shoudl be replicated accross -->
```

## Sharding config
### readBehaviour
* roundRobin (default)
* zone

### writeBehaviour
* roundRobin (default)
* freeSpace
* percentageFreeSpace
* zone

### readBehaviour
* roundRobin (default)
* zone

e.g.
![sample shard config](https://github.com/khylo/CourseNotes/blob/[branch]/image.jpg?raw=true)

