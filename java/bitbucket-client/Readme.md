# Getting Started
This projet generates a java client from the bitbucket openapi spec.
This document is downloaded from https://developer.atlassian.com/server/bitbucket/rest/v810/intro/
(Click on 3 dots and dowload api spec).

Note the first version of this appears to be 8.10. We copied this and manually made (at this moment 1) adjustment to make it compatibile with 7.21. 
Note we also updated the version field to take its value from the pom.xml (See ${project.version} field in 7.21*.json).. In order  to get this processed we need to include the resources:resources phaes.

Note for the Atlassian openapi docs we have specified skipValidation as by default it was generating errors

Also note in the plugin section we have set the target packages of the generated-sources
'''
mvn clean resources:resources generate-sources
'''

This will generate the client source code.

To then build/ deploy that use something like
```
mvn -f target/generated-sources/openapi/pom.xml -DskipTests -Dmaven.javadoc.skip=true -Dmaven.javadoc.skip package
```

Also added github action to build
### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.6/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.6/maven-plugin/reference/html/#build-image)

