# Vault

## Certification

### Objectives
| 1  | Exam Objectives                   |   |   |   |
|----|---------------------------------------------------|---|---|---|
| 1	 | Compare authentication methods |   |   |   |
| 1a	 | Describe authentication methods |   |   |   |
| 1b	 | Choose an authentication method based on use case |   |   |   |
| 1c	 | Differentiate human vs. system auth methods |   |   |   |
| 2	 | Create Vault policies |   |   |   |
| 2a	 | Illustrate the value of Vault policy |   |   |   |
| 2b	 | Describe Vault policy syntax: path |   |   |   |
| 2c	 | Describe Vault policy syntax: capabilities |   |   |   |
| 2d	 | Craft a Vault policy based on requirements |   |   |   |
| 3	 | Assess Vault tokens |   |   |   |
| 3a	 | Describe Vault token |   |   |   |
| 3b	 | Differentiate between service and batch tokens. Choose one based on use-case |   |   |   |
| 3c	 | Describe root token uses and lifecycle |   |   |   |
| 3d	 | Define token accessors |   |   |   |
| 3e	 | Explain time-to-live |   |   |   |
| 3f	 | Explain orphaned tokens |   |   |   |
| 3g	 | Create tokens based on need |   |   |   |
| 4	 | Manage Vault leases |   |   |   |
| 4a	 | Explain the purpose of a lease ID |   |   |   |
| 4b	 | Renew leases |   |   |   |
| 4c	 | Revoke leases |   |   |   |
| 5	 | Compare and configure Vault secrets engines |   |   |   |
| 5a	 | Choose a secret method based on use case |   |   |   |
| 5b	 | Contrast dynamic secrets vs. static secrets and their use cases |   |   |   |
| 5c	 | Define transit engine |   |   |   |
| 5d	 | Define secrets engines |   |   |   |
| 6	 | Utilize Vault CLI |   |   |   |
| 6a	 | Authenticate to Vault |   |   |   |
| 6b	 | Configure authentication methods |   |   |   |
| 6c	 | Configure Vault policies |   |   |   |
| 6d	 | Access Vault secrets |   |   |   |
| 6e	 | Enable Secret engines |   |   |   |
| 6f	 | Configure environment variables |   |   |   |
| 7	 | Utilize Vault UI |   |   |   |
| 7a	 | Authenticate to Vault |   |   |   |
| 7b	 | Configure authentication methods |   |   |   |
| 7c	 | Configure Vault policies |   |   |   |
| 7d	 | Access Vault secrets |   |   |   |
| 7e	 | Enable Secret engines |   |   |   |
| 8	 | Be aware of the Vault API |   |   |   |
| 8a	 | Authenticate to Vault via Curl |   |   |   |
| 8b	 | Access Vault secrets via Curl |   |   |   |
| 9	 | Explain Vault architecture |   |   |   |
| 9a	 | Describe the encryption of data stored by Vault |   |   |   |
| 9b	 | Describe cluster strategy |   |   |   |
| 9c	 | Describe storage backends |   |   |   |
| 9d	 | Describe the Vault agent |   |   |   |
| 9e	 | Describe secrets caching |   |   |   |
| 9f	 | Be aware of identities and groups |   |   |   |
| 9g	 | Describe Shamir secret sharing and unsealing |   |   |   |
| 9h	 | Be aware of replication |   |   |   |
| 9i	 | Describe seal/unseal |   |   |   |
| 9j	 | Explain response wrapping |   |   |   |
| 9k	 | Explain the value of short-lived, dynamically generated secrets |   |   |   |
| 10	 | Explain encryption as a service |   |   |   |
| 10a	 | Configure transit secret engine |   |   |   |
| 10b	 | Encrypt and decrypt secrets |   |   |   |


Vault Associate: https://learn.hashicorp.com/tutorials/vault/associate-study

Vault fundamentals
Objectives covered: 1a-c, 2b-c, 3a-g, 4a-c, 5a, 6a-f, 7a-e, 8a-b, 9a, 9c, 9g, 9i, 9k

You will be tested on your knowledge of Vault fundamentals which include Vault architecture, seal/unseal Vault, and how to authenticate with Vault. Complete the following tasks to ensure that you understand the Vault core concepts.

Complete the Vault Getting Started tutorials
Read the Introduction to Vault documentation
Read the Vault Concepts documentation to make sure that you understand the core concepts
Review the Overview section of the Vault Commands (CLI) documentation to understand the basic structure of CLI


## Vault Tutorials
https://learn.hashicorp.com/collections/vault/getting-started


## Introduction to vault
https://learn.hashicorp.com/tutorials/vault/getting-started-intro?in=vault/getting-started

Vault manages 3 things 
Centralized secret store.
Tightaccess controls to these secrets/ ephermeral keys (short lived tokens)
Libraries for encryption

* Vault is secret maangement. Central control for Secrets encrypted at rest and in transit.
* Allow access management to it.
* Also allows dynamic secrets.. Secrets are only vialbe for short time (tokens)
* Have unique credentials for each client.. Thus manage exposure much easier.
* Encrypt as a service.. so it can do crypography
* Key lifecycle management


Vault has its core plus plugins

#### Auth plugn
* Intefaces between different systems and vault code. e.g. Aws/ Ldap/ K8s  
* Audit plugin for auidting access
* storage e.g. db for storing secrets at rest  / Secret like rabbitMQ / consul etc
* 
[<img src="https://mktg-content-api-hashicorp.vercel.app/api/assets?product=tutorials&version=main&asset=public%2Fimg%2Fvault%2Fvault-triangle.png">](https://learn.hashicorp.com/tutorials/vault/getting-started-intro?in=vault/getting-started)

### Vault CLI
```
vault server -dev
set VAULT_ADDR=http://127.0.0.1:8200
set VAULT_TOKEN=<token printerd from server -dev>
vault status
vault secrets enable -version=2 kv #Enable v2 secrets engine
vault kv put -mount=secret foo bar=baz  # put foo in mount secret with bar=baz
vault kv get -mount=secret foo #Read this value back
vault kv metadata get -mount=secret foo # Get metadata for the key:
vault kv get -mount=secret -version=1 foo # Get a specific version of the key

vault kv put -mount=secret hello foo=world excited=yes # multi[le entries
vault kv put -mount=secret hello foo=world excited=yes
# The deprecated path-like syntax can also be used, but this should be avoided
#  for KV v2, as the fact that it is not actually the full API path to
#  the secret (secret/data/foo) can cause confusion:
 vault kv get secret/foo
```
