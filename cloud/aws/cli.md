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

##jq
The command-line JSON processor, jq[2], is “like sed for JSON data -
  you can use it to slice and filter and map and transform structured
  data with the same ease that sed, awk, grep and friends let you play
  with text.” With just a few operators you can do amazing things such
  as transform lists into CSV output (shown below).

    $ echo '{"x":["a","b"], "y":["d","e"]}' | jq -r '.[] | @csv'
    "a","b"
    "d","e"
	
aws iam list-roles --output=json | jq -r -f ~/aws-iam-roles-to-csv.jq 

aws-iam0roles-to-csv.jq
["Roles", "Effect", "Federated"],   # Output header
        (
            .Roles[] |                      # For each role ...
            .RoleName as $role |            # ... store role name
            .AssumeRolePolicyDocument |     # Descend into structure
            .Statement[] |                  # For each policy statement ...
            .Effect as $effect |            # ... store effect
            .Principal |                    # Descend into structure
            select(.Federated) |            # Continue *if* Federated exists
            .Federated |                    # For each Federated value ...
            if type == "string" then        # If value is string ...
                [.]                         # ... create Federated list
            else                            # Otherwise ...
                .                           # ... output list
            end |
            .[] |                           # For each Federated item ...
            [$role, $effect, .]             # ... output item list
        ) | @csv                            # Convert all output to CSV


