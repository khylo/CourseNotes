# Getting Started
This projet generates a java client from the bitbucket openapi spec.
This document is downloaded from https://developer.atlassian.com/server/bitbucket/rest/v810/intro/
(Click on 3 dots and dowload api spec)

Note for the Atlassian openapi docs we have specified skipValidation as by default it was generating errors

Also note in the plugin section we have set the target packages of the generated-sources
'''
mvn clean generate-sources
'''

This will generate the client source code.

To then build/ deploy that use something like
```
mvn -f target/generated-sources/openapi/pom.xml -DskipTests install
```
### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.6/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.6/maven-plugin/reference/html/#build-image)

