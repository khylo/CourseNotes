Odta is an OASIS standard for data interchange. It supports Rest ans well as other data formats.
I

See https://www.baeldung.com/odata
and for code examples using olingo
https://www.baeldung.com/olingo


Heres an example of sintegration of olingo and spring boot
https://blogs.sap.com/2021/10/29/building-an-odata-service-with-a-spring-java-application-using-olingo-part-i/ 


# Overview

Metadata describes the data
It will return a list of DataServices
Each of these will contain

* Entity type 
* Associations / RElationships with other enttities , e.g. one to many etc
* EntoySet element for collections

# Sample OData sources
Publicaly available oData serer
curl -s https://services.odata.org/V2/Northwind/Northwind.svc/Regions

List of other OData sources
https://pragmatiqa.com/xodata/odatadir.html

