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
vault server --dev --dev-root-token-id="$VAULT_DEV_ROOT"
````
#expose VAULT token in appliation.properties
mvn spring-boot:run

Add secrets
```
vault kv put secret/gs-vault-config example.username=demouser example.password=demopassword
vault kv put secret/gs-vault-config/cloud example.username=clouduser example.password=cloudpassword
vault kv put secret/kv keystore.password=changeit db.password=dbpass
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

# CLI
curl 'https://ie.thesalarycalculator.co.uk/salary.php' \
-H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7' \
-H 'Accept-Language: en-US,en;q=0.9' \
-H 'Cache-Control: max-age=0' \
-H 'Connection: keep-alive' \
-H 'Content-Type: application/x-www-form-urlencoded' \
-H 'DNT: 1' \
-H 'Origin: https://ie.thesalarycalculator.co.uk' \
-H 'Referer: https://ie.thesalarycalculator.co.uk/salary.php' \
-H 'Sec-Fetch-Dest: document' \
-H 'Sec-Fetch-Mode: navigate' \
-H 'Sec-Fetch-Site: same-origin' \
-H 'Sec-Fetch-User: ?1' \
-H 'Upgrade-Insecure-Requests: 1' \
-H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36' \
-H 'sec-ch-ua: "Not_A Brand";v="8", "Chromium";v="120", "Google Chrome";v="120"' \
-H 'sec-ch-ua-mobile: ?0' \
-H 'sec-ch-ua-platform: "Windows"' \
--data-raw 'salary=18304&status=0&age=low&pension=&taxCredit=3550&taxallowance=&chosenTaxYear=2023&submit=Go%21&timeperiods%5B%5D=1&timeperiods%5B%5D=12&timeperiods%5B%5D=52&timeperiods%5B%5D=260&submit=' \
--compressed

# Bash to parse
#!/bin/bash

# Extract the yearly values for Gross, tax, usc, prsi, and net pay
gross=$(echo "$1" | pup 'tr.gross.normal td:nth-child(2) text{}')
tax=$(echo "$1" | pup 'tr.tax.normal td:nth-child(2) text{}')
usc=$(echo "$1" | pup 'tr.USC.normal td:nth-child(2) text{}')
prsi=$(echo "$1" | pup 'tr.PRSI.grey td:nth-child(2) text{}')
net_pay=$(echo "$1" | pup 'tr.takehome.normal td:nth-child(2) text{}')

# Print the extracted values
echo "Gross: **$gross**"
echo "Tax: **$tax**"
echo "USC: **$usc**"
echo "PRSI: **$prsi**"
echo "Net Pay: **$net_pay**"

curl -s "https://example.com" | ./extract.sh
