
On new server:

sudo yum install docker  httpd httpd-tools mod_auth_gssapi mod_ssl -y
sudo docker run -d --name krb5-server -e KRB5_REALM=EXAMPLE.COM -e KRB5_KDC=localhost -e KRB5_PASS=mypass -p 88:88 -p 464:464 -p 749:749 gcavalcante8808/krb5-server
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
  add_principle httpd
  # Pword httpd
  
ktutil
    add_entry -password -p httpd@EXAMPLE.COM -k 1 -e arcfour-hmac-md5

 wkt /tmp/httpd.keytab


sudo vi /etc/httpd/conf/httpd.conf
<Location /private>
    AuthType GSSAPI
    AuthName "GSSAPI Single Sign On Login"
    GssapiCredStore keytab:/etc/httpd.keytab
    Require valid-user
</Location>



Trying to test with curl.. See
https://docs.cloudera.com/cdp-private-cloud-base/7.1.6/scaling-namespaces/topics/hdfs-curl-url-http-spnego.html
