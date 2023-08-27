# Containers
Allow us to minimize "it works on my machine" issues.
Virtual Environment that allows us to standardize setup including.
 - Runtime Engine (java version node version etc)
 - Dependencies (OS tweeks you may need. e.g. java )
 - Code
 - Network Connectivity / OS version.


## Container Image
 - Readonly image that is used as a template
 - Start from base images that have your dependencies (java, curl, node etc)  add your custom code.
     - Typically things that change more are nearer the top of the layer model. e.g. new version of app shoudl go at top level, so changes can just build on other layers.
 - DOckerfile for easy reporiducible builds.

 Four environment, same container.


 # Amazon Container tech
 - ECr Elastic Container REgistry.. 
 - ECS   Elastic Container SErvice
 - EKS Elastic K7s Service
 - Fargate
 - Ec2

 # Kubernetes K8S
 - Open Source container management platform
 - Load balancer
 - Kubernetes Service concept. Gives a name to your pod (app1.ns). This is then exposed as loadbalenced endpoint. http://app1.ns. THis will load balnace accross all pods. With K8sSerice you can specify if pod has external internet access / added to LB 
 - Manages intances / Auto Scaling vertically and horiontally .

 - Control Plane. Brain of engine works here to mange apps#
      -- Api server 
      -- Scheduler
      -- etcd   Perstistence store
 - Data / Worker plane. Actual workload run on nodes (e..g Ec2 or fargate)
    - Kubelet.  Local controller. Talks to control plane. Sends healthcheck etc. EKS manages setting this up on Nodes. Alternative is to manually do thi.
    - K Proxy
 - Pod is smallest unit of deployment. Pod can contain 1 or more containers. Recommended to use 1 pod per container. In java this would be a jar deployment in a container. WHy have multiple containers ? e.g. if there was tighly couped microservices they can be accomodated. 
 - ReplicaSet. Kubernets way to manage replicas / multiple pods.
 - REcommend stateless containers / pods. Have them use persistant store for state. THay way pods can be rescheduld

# EKS
AWS managed K8s
4 versions supported, giving you time to upgrade
EKS manages the control plane componenets. THis is differnet to your VPC / AWS account. It will point to your VPC instances. FOr fargate it uses microVms in control plane by default.
- Ec2 Based nodes
    - Can have self managed node groups or
    - Ec2 Managed Node GRoups
- Serverless (fargate) 
 
 EKS architecure . Multiple AZs . It will not be shared with other control Planes. Created for you individually.
 99.95% SLA


 # Lab1
 Using cloud9 Dev Env, to clone repo.
 https://catalog.us-east-1.prod.workshops.aws/join
f0ea-087fcf-d4

 https://us-east-1.console.aws.amazon.com/cloud9/ide/298250ea09ba4a18994f4dfdfcab2603

git clone https://github.com/aws-samples/amazon-ecs-mythicalmysfits-workshop.git

script/sestup
Sets up dynamo DB   
```
#! /bin/bash

set -eu

echo "Removing unneeded docker images..."
docker images -q | xargs docker rmi || true

echo "Installing dependencies..."
sudo yum install -y jq

echo "Fetching CloudFormation outputs..."
script/fetch-outputs

echo "Populating DynamoDB table..."
script/load-ddb

echo "Uploading static site to S3..."
if [[ $# -eq 1 ]]; then
  script/upload-site $1
else
  script/upload-site
fi

echo "Installing ECR Cred Helper..."
sudo script/credhelper

echo "Attaching Instance Profile to Cloud9..."
script/associate-profile

echo "Success!"

```

SEtup Env
```
export ACCOUNT_ID=$(aws sts get-caller-identity --output text --query Account)
export AWS_REGION=$(curl -s 169.254.169.254/latest/dynamic/instance-identity/document | jq -r '.region')

echo "export ACCOUNT_ID=${ACCOUNT_ID}" >> ~/.bash_profile
echo "export AWS_REGION=${AWS_REGION}" >> ~/.bash_profile
aws configure set default.region ${AWS_REGION}
aws configure get default.region
`

Key Mgmt
```
ssh-keygen

aws ec2 import-key-pair --key-name "mythicaleks" --public-key-material file://~/.ssh/id_rsa.pub

docker --version
docker pull nginx\:latest
docker run -d -p 8080:80 --name nginx nginx\:latest
docker logs nginx
docker exec -it nginx /bin/bash
#Note image does not even have vi installed
docker stop nginx
docker ps -a
docker rm nginx
docker rmi nginx\:latest
```

### Build Container Image
````
docker history nginx:1.0
# STore index somewhere else
docker run -d -p 8080:80 -v /home/ec2-user/environment/container-image/index.html:/usr/share/nginx/html/index.html\:ro --name nginx nginx\:latest

```
Complete dockerfile
```
FROM ubuntu:20.04
RUN apt-get update -y
RUN apt-get install -y python3-pip python-dev build-essential
RUN pip3 install --upgrade pip
#[TODO]: Copy python source files and requirements file into container image
COPY ./service /MythicalMysfitsService
WORKDIR /MythicalMysfitsService
#[TODO]: Install dependencies listed in the requirements.txt file using pip3
RUN pip3 install -r ./requirements.txt
#[TODO]: Specify a listening port for the container
EXPOSE 80
#[TODO]: Run the mythicalMysfitsService.py as the final step
ENTRYPOINT ["python3"]
CMD ["mythicalMysfitsService.py"]

```


Run in background
TABLE_NAME=$(aws dynamodb list-tables | jq -r .TableNames[0])
docker run -d -p 8000:80 -e AWS_DEFAULT_REGION=$AWS_REGION -e DDB_TABLE_NAME=$TABLE_NAME monolith-service

Existing repositorie
211929952315.dkr.ecr.us-east-1.amazonaws.com/containersid-mono-kw5sxmiclgjk

# lab5 section 
https://catalog.us-east-1.prod.workshops.aws/event/dashboard/en-US/workshop/eks/launch-eks
THis uses eksctl command to configure a cluster. Alternatives are awsCDK https://aws.amazon.com/cdk/, and terraform.

## PreREqs
To help facilitate the workshop and interactions with your Amazon EKS clusters, we will install the following binaries:

awscliv2  - Used to manage AWS services. This lab will also make use the AWS IAM Authenticator for Kubernetes  in the AWS CLI.
eksctl  - Used to create and manage Amazon EKS clusters.
kubectl  - Provides a command-line interface for communicating with the Kubernetes control plane.
helm  - Kubernetes application management utility.
jq  - Command-line JSON processor.
yq  - Command-line YAML processor. Note that we will install this as a convenience - it is not specifically required for this workshop.
bash-completion  and envsubst  - Utilities to help with shell commands.

``` 
cd ~/environment

# install or upgrade the aws cli
sudo pip uninstall -y awscli
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip -o awscliv2.zip
sudo ./aws/install --update
. ~/.bash_profile

# install eksctl
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv -v /tmp/eksctl /usr/local/bin
eksctl version || ( echo "eksctl not found" && exit 1 )

# install kubectl
sudo curl --silent --location -o /usr/local/bin/kubectl https://s3.us-west-2.amazonaws.com/amazon-eks/1.22.6/2022-03-09/bin/linux/amd64/kubectl
sudo chmod +x /usr/local/bin/kubectl
kubectl version --client=true || ( echo "kubectl not found" && exit 1 )

# install helm
curl https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3 | bash

# install additional tools
sudo yum -y install jq gettext bash-completion moreutils

# enable bash completion
kubectl completion bash >>  ~/.bash_completion
eksctl completion bash >> ~/.bash_completion
. ~/.bash_completion

# install yq
echo 'yq() {
 docker run --rm -i -v "${PWD}":/workdir mikefarah/yq yq "$@"
}' | tee -a ~/.bashrc && source ~/.bashrc

# make sure all binaries are in the path
for command in kubectl jq envsubst aws eksctl kubectl helm
  do
    which $command &>/dev/null && echo "$command in path" || ( echo "$command NOT FOUND" && exit 1 )
  done

echo 'Prerequisites installed successfully.'

```

# Get list of nodes with extra info
kubectl get nodes -o wide


aws eks get-token --cluster-name=

# Lab 6
In this lab, we are going to deploy the AWS Load Balancer Controller  - which will be used to expose our applications externally.

# Lab7
In this lab, we are going to deploy the containerized version of the monolith application as a Kubernetes pod running in your Amazon EKS cluster.

```
cat << EOF > monolith-app.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
 name: mythical-mysfits-eks
 namespace: default
 labels:
   app: mythical-mysfits-eks
spec:
 replicas: 2
 selector:
   matchLabels:
     app: mythical-mysfits-eks
 template:
   metadata:
     labels:
       app: mythical-mysfits-eks
   spec:
     serviceAccount: mythical-misfit
     containers:
       - name: mythical-mysfits-eks
         image: $MONO_ECR_REPOSITORY_URI:latest
         imagePullPolicy: Always
         ports:
           - containerPort: 80
             protocol: TCP
         env:
           - name: DDB_TABLE_NAME
             value: ${TABLE_NAME}
           - name: AWS_DEFAULT_REGION
             value: ${AWS_REGION}
---
apiVersion: v1
kind: Service
metadata:
 name: mythical-mysfits-eks
 namespace: default
spec:
 type: LoadBalancer
 selector:
   app: mythical-mysfits-eks
 ports:
 -  protocol: TCP
    port: 80
    targetPort: 80
EOF

```

kubectl apply -f monolith-app.yaml

curl -o iam-policy.json https://raw.githubusercontent.com/kubernetes-sigs/aws-load-balancer-controller/main/docs/install/iam_policy.json


#create the policy
aws iam create-policy --policy-name AWSLoadBalancerControllerIAMPolicy --policy-document file://iam-policy.json

#get the policy ARN
export PolicyARN=$(aws iam list-policies --query 'Policies[?PolicyName==`AWSLoadBalancerControllerIAMPolicy`].Arn' --output text)
echo $PolicyARN

# create service account 
eksctl create iamserviceaccount \
   --cluster=mythicaleks-eksctl \
   --namespace=kube-system \
   --name=aws-load-balancer-controller \
   --attach-policy-arn=$PolicyARN \
   --override-existing-serviceaccounts \
   --approve
