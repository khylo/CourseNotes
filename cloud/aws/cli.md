# CLI

##S3
aws configure
aws s3 ls   # list buckets
aws s3 cp --recursive s3://khylo s3://khylo-syd   # copy from one to another
..* if oyu get an error about Signature Version 4, then you need to specigy thje --region argument
aws s3 cp --recursive s3://khylo s3://khylo-syd  --region eu-west-1 # Note some regions insist you have the --region flag set. But not all (weird).. set region to the region where the bucket is
..* Can also do to local , or ec2 instance
aws s3 cp --recursive s3://khylo .

Control List and Bucket policie

s3, S3IA, S3 IA 1 td, expedited, bulkaz, glacier (3 retrieveal types s,  3 - 5 hrs.
bucket is like a folder but unique and routable to  

bucket corresponds to url... https://s3-amazonaws.com/<name> e.g. https://s3-eu-west-1.amaonaws.com   


s3 global though??   Wrong.   S3 managment is global but the buckets are in a region.

##EC2
aws ec2 describe-instances
aws ec2 start-instances --instance-ids=i-0621e8fd49f78d318
aws ec2 terminate-instances --instance-ids=i-0621e8fd49f78d318

Note vwhen you create an ec2 instance if its AMI type then it will have aws cli installed.
You should not pass in credentials (via aws configure) . Instead use apply rules to this instance to give it access to services, e.g. S3 etc.
goto Ec2/ Actions / Instance Settings/ Attach/ Replace IAM roles .

Can add / rmove roles to running instance.. Once it has roles it does not need credentials

e.g. s3 roles mean it can run aws s3 ..., ec2 roles would mean aws ec2 ...