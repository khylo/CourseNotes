# Steps for configuring Kerberos
We are setting up a docker instanse running Kerberos server/ Key Distribution Center (KDC)
On the same machine we have a Kerberos / httpd client running gssapi

# Client Server Setup
On new server:
```
sudo yum install docker  httpd httpd-tools mod_auth_gssapi mod_ssl -y
sudo docker run -d --name krb5-server -e KRB5_REALM=EXAMPLE.COM -e KRB5_KDC=localhost -e KRB5_PASS=mypass -p 88:88 -p 464:464 -p 749:749 gcavalcante8808/krb5-server
# To get interactive access to docker do
# sudo docker exec -it <containerID> /bin/ash
 sudo docker exec -it 0d2b8322e197 /bin/ash
sudo yum install krb5-workstation krb5-libs
sudo docker logs krb5-server
sudo su -
#EDIT /etc/krb5.conf

[libdefaults]
 dns_lookup_realm = false
 ticket_lifetime = 24h
 renew_lifetime = 7d
 forwardable = true
 rdns = false
 default_realm = EXAMPLE.COM

[realms]
 EXAMPLE.COM = {
    kdc = localhost
    admin_server = localhost
 }

kinit admin/admin@EXAMPLE.COM # Will prompt for the password provided or the generated.
klist

# Generate keytab . See https://www.ibm.com/docs/en/spectrum-symphony/7.1.2?topic=file-creating-kerberos-principal-keytab
kadmin -p admin/admin@EXAMPLE.COM
  add_principal  httpd
  # Pword httpd
  
sudo ktutil
    add_entry -password -p httpd@EXAMPLE.COM -k 1 -e arcfour-hmac-md5
    add_entry -password -p httpd/httpd@EXAMPLE.COM -k 1 -e arcfour-hmac-md5

 wkt /etc/httpd.keytab
 wkt /etc/httpd-httpd.keytab

 kinit httpd/httpd@EXAMPLE.COM # Will prompt for the password provided or the generated.


sudo vi /etc/httpd/conf/httpd.conf

<Location /private>
    AuthType GSSAPI
    AuthName "GSSAPI Single Sign On Login"
    GssapiCredStore keytab:/etc/httpd.keytab
    Require valid-user
</Location>

#Kerberos Principal names are in the format.   servicename/hostname@realms
# So in this example we se this in klist   ::    krbtgt/EXAMPLE.COM@EXAMPLE.COM
# Not sure what krbgt is?   but/EXAMPLE.COM is hostname and EXAMPLE.COM is realm

```


Trying to test with curl.. See
https://docs.cloudera.com/cdp-private-cloud-base/7.1.6/scaling-namespaces/topics/hdfs-curl-url-http-spnego.html

curl --negotiate -u :  http://localhost/private  



## Kerberos commands
See https://docs.oracle.com/cd/E19253-01/816-4557/refer-5/index.html
Also using https://www.youtube.com/watch?v=dvYkcZATY6k&ab_channel=BensonYerimah
https://web.mit.edu/kerberos/krb5-latest/doc/user/user_commands/index.html

*kadmin*   Remote Kerberos database administration program (run with Kerberos authentication), which is used to manage principals, policies, and keytab files
*kadmin.local*  Local Kerberos database administration program (run without Kerberos authentication and must be run on master KDC), which is used to manage principals, policies, and keytab files
*kclient*  Kerberos client installation script which is used with or without a installation profile
*kinit* Creates and stores tickets
*kdestroy*   Destroys tickets
*klist*   Displays current Kerberos tickets
*kpasswd*   Change kerberos password
*kprop*   Kerberos database propagation program
*ktutil*  Manages Kerberos keytab files
*kdb5_util*  Not on Kerberos page. Apparently handle create , dump, load and destroy .See  https://youtu.be/dvYkcZATY6k?t=1274 