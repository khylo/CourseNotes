Project to setup server that accepts auth tokens from 2 different providers

1. AuthServer
authServer is a spring boot authSErver. Invoke it with 
```mvn spring-boot:run```
It will run on port 9000  by default. THe users are configured in the application.yaml. Login user/password

1. KeyCloak
Keycloak is a JVM authserver from jboss. See https://www.baeldung.com/spring-boot-keycloak

We invoke it via the docker compose file. It runs on port 8888 by default (login admin/admin)

To start run 
```
# If needed install docker compose
#
#sudo curl -L "https://github.com/docker/compose/releases/download/v2.32.4/docker-compose-$(uname -s)-$(uname -m)"  -o /usr/local/bin/docker-compose
#sudo mv /usr/local/bin/docker-compose /usr/bin/docker-compose
#sudo chmod +x /usr/bin/docker-compose   
export KEYCLOAK_ADMIN_PASSWORD=admin
docker-compose up -d
```

1. JWT
jwt is the server that accepts different tokens
the sendond provider is embeded in jwt (keycloak)