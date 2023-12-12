# Getting Started
https://spring.io/guides/gs/vault-config/
### Reference Documentation

wload and instll vault
https://developer.hashicorp.com/vault/install?product_intent=vault#Linux
```
wget -O- https://apt.releases.hashicorp.com/gpg | sudo gpg --dearmor -o /usr/share/keyrings/hashicorp-archive-keyring.gpg
echo "deb [signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] https://apt.releases.hashicorp.com $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/hashicorp.list
sudo apt update && sudo apt install vault
```

Start Vault in Dev mode, and save unsealKEy
````
export VAULT_ADDR='http://127.0.0.1:8200'
echo "GmUsB6tYWVFdfQactUK+2QFJ0zcPlRe2Agt7rp5neW8=" > unseal.key
export VAULT_DEV_ROOT_TOKEN=hvs.dfw6J4Ozm3vQCpV3AOqZAHte
vault status
````
#expose VAULT token in appliation.properties
mvn spring-boot:run

Add secrets
```
vault kv put secret/gs-vault-config example.username=demouser example.password=demopassword
vault kv put secret/gs-vault-config/cloud example.username=clouduser example.password=cloudpassword
vault kv put secret/kv keystore.password=changeit
```

By default, Spring Vault will look up secrets in the /secret/{application}/{profile} path, where application is the value of the spring.application.name property and profile is the value of the spring.profiles.active property.

However, you can override this default path by setting the spring.cloud.vault.kv.application-name property to a different value. For example, if you want to look up secrets in the /secret/my-app/prod path, you would set the following property in your application.yml file:

In addition, you can also set the spring.cloud.vault.kv.profiles property to specify a list of profiles to use when looking up secrets. 

# Multiple Backends
See https://cloud.spring.io/spring-cloud-vault/multi/multi_vault.config.backends.html
In this example we overload the default secret location (which is taken from the application.name normally) using this we add gs-vault-config, and kv 
spring.cloud.vault.application-name: gs-vault-config,kv,salary

# Create Keystore
keytool -genkey -alias clientCert -keyalg rsa -keystore keystore.jks