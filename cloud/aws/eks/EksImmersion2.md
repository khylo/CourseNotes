# Aws Immersion DAy 2

THis is linked from here: 
http://catalog.us-east-1.prod.workshops.aws/join?access-code=e6a1-0c1535-b2

Course is here:
https://catalog.us-east-1.prod.workshops.aws/event/dashboard/en-US/workshop

Console is here:
https://us-east-1.console.aws.amazon.com/console/home?region=us-east-1#



Navigate to Cloud 9  in AWS Console.
https://eu-north-1.console.aws.amazon.com/cloud9/home?region=eu-north-1
make sure you are on N Virginia US-East-1

WE will cover

Helm.. See https://helm.sh/docs/chart_template_guide/getting_started/

RBAC
IAM Role for Service Account IRSA

HeathCheck
Observability

EFS
Secrets and secret manaager
Cost Manager

## Start
https://docs.google.com/document/d/171w8Kiu2dbwSjOuVWUhMzOf-YhQYwu0JvcQtd5QJVeQ/edit
Manual setup
```
cd ~/environment
git clone https://github.com/aws-containers/eks-app-mesh-polyglot-demo.git
cd eks-app-mesh-polyglot-demo

curl -sSL https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3 | bash

cd ~/environment/eks-app-mesh-polyglot-demo
helm install --debug --dry-run workshop ~/environment/eks-app-mesh-polyglot-demo/workshop/helm-chart/

# Now run again without dry-run

kubectl get pod,svc -n workshop -o wide

```
Helm  is the package manager for Kubernetes. Helm is the best way to find, share, and use software built for Kubernetes. Helm and Kubernetes work like a client/server application. The Helm client pushes resources to the Kubernetes cluster via kubernetes API.

kubectl get nodes


## RBAC
Role-based access control (RBAC) is a method of regulating access to computer or network resources based on the roles of individual users within an enterprise.
Fine-Grained IAM Roles for Service Accounts
You can associate an IAM role with a Kubernetes service account. This service account can then provide AWS permissions to the containers in any pod that uses that service account. With this feature, you no longer need to provide extended permissions to the Amazon EKS node IAM role so that pods on that node can call AWS APIs. The applications in the pod’s containers can then use an AWS SDK or the AWS CLI to make API requests to authorized AWS services.

The IAM roles for service accounts feature provides the following benefits:

Least privilege — By using the IAM roles for service accounts feature, you no longer need to provide extended permissions to the node IAM role so that pods on that node can call AWS APIs. You can scope IAM permissions to a service account, and only pods that use that service account have access to those permissions.

Credential isolation — A container can only retrieve credentials for the IAM role that is associated with the service account to which it belongs. A container never has access to credentials that are intended for another container that belongs to another pod.
EKS has 3 types of Auth

- Service Axxount auth using bearer toen
- IAM
- OIDC identity provider auth.. Added in 2021.. OpenId. Kubernetes native. use kubectl login

EKS relies on native Kubernetes RBAC for authorization

### IAM


kubectl pases aws identity to K8s api. K8s verifies Aws identity with IAM Authenticator.
Role bindings or cluster roles bindings

kubectl gett pod
get Auth token from IAM-authenticator

### OIDC
NEed OIDC Auth plugin

### Auth usign Kubernetes RBAC

K8s has 
- roles which are defineid and apply to a namespace (virtual cluster)
- CLusterROles which apply cluster wide accross all namespaces

CustomRoles are defined (in yaml) describing resources (pods / nodes) and verbs (get, update delete) are allowed against them.
#### Roles
Roles give fine grainied control.
e.g. can assign role to individual pods rather thean adjust the whole Ec2 IAM role that effects all pods.
e.g. create IAM role
all ops for pod
all ops for deployment
get, list for ConfigMap

Then define RoleBinding
If these are defined in NS dev
then
kubectl get pods -n dev # Works
kubectl get pods -n prod # fails since diff NS
#### ClusterRole
Define ClusterROle and then ClusterRole Binding.


Create IAM User
Create K8s user
Map K8s user to IAM user (configMap)

Create Role / Rolebinding or clusterRoles/ ClusterRole Binding

### IAM roles for Service Accounts
STS SEcure Token Service
OIDC provider

IRSA
Need to create OIDC

## Lab
```
# Create user and save credentials
aws iam create-user --user-name rbac-user
aws iam create-access-key --user-name rbac-user | tee /tmp/create_output.json

# Create script for switching users
cat << EoF > rbacuser_creds.sh
export AWS_SECRET_ACCESS_KEY=$(jq -r .AccessKey.SecretAccessKey /tmp/create_output.json)
export AWS_ACCESS_KEY_ID=$(jq -r .AccessKey.AccessKeyId /tmp/create_output.json)
EoF
# Map user to K8s
eksctl create iamidentitymapping \
  --cluster eksworkshop-eksctl \
  --arn arn:aws:iam::${ACCOUNT_ID}:user/rbac-user \
  --username rbac-user

#TEst
kubectl describe cm aws-auth -n kube-system 

# Swtich user and test again.. Fail. Need to create role an drole binding
. rbacuser_creds.sh
kubectl describe cm aws-auth -n kube-system 
aws sts get-caller-identity
# Create role and rolebinding
unset AWS_SECRET_ACCESS_KEY
unset AWS_ACCESS_KEY_ID
# Check we've changed back to admin
aws sts get-caller-identity
#Create Role
cat << EoF > rbacuser-role.yaml
kind: Role
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  namespace: workshop
  name: pod-reader
rules:
- apiGroups: [""] # "" indicates the core API group
  resources: ["pods"]
  verbs: ["list","get","watch"]
- apiGroups: ["extensions","apps"]
  resources: ["deployments"]
  verbs: ["get", "list", "watch"]
EoF
# Create RoelBinding
cat << EoF > rbacuser-role-binding.yaml
kind: RoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: read-pods
  namespace: workshop
subjects:
- kind: User
  name: rbac-user
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: Role
  name: pod-reader
  apiGroup: rbac.authorization.k8s.io
EoF

# Apply role and RB
kubectl apply -f rbacuser-role.yaml
kubectl apply -f rbacuser-role-binding.yaml

# witch user
. rbacuser_creds.sh; aws sts get-caller-identity
kubectl get pods -n workshop
# Try with diff NS fails

kubectl get pods -n kube-system

# CLeanup
unset AWS_SECRET_ACCESS_KEY
unset AWS_ACCESS_KEY_ID
rm rbacuser_creds.sh
rm rbacuser-role.yaml
rm rbacuser-role-binding.yaml
aws iam delete-access-key --user-name=rbac-user --access-key-id=$(jq -r .AccessKey.AccessKeyId /tmp/create_output.json)
aws iam delete-user --user-name rbac-user
rm /tmp/create_output.json
eksctl delete iamidentitymapping --cluster eksworkshop-eksctl --arn arn:aws:iam::${ACCOUNT_ID}:user/rbac-user
# Verify
aws sts get-caller-identity



``` 

## Enable IRSA
```
# Create OIDC provider.. 
aws eks describe-cluster --name eksworkshop-eksctl --query cluster.identity.oidc.issuer --output text
  eksctl utils associate-iam-oidc-provider --cluster eksworkshop-eksctl --approve
# Lookup S3 REad access role arn
aws iam list-policies --query 'Policies[?PolicyName==`AmazonS3ReadOnlyAccess`].Arn'
#Create IAM roles and bind it to S3 qaccess
eksctl create iamserviceaccount \
    --name iam-test \
    --namespace workshop \
    --cluster eksworkshop-eksctl \
    --attach-policy-arn arn\:aws\:iam::aws\:policy/AmazonS3ReadOnlyAccess \
    --approve \
    --override-existing-serviceaccounts
# Associate IAM roles with SErvice Account
kubectl describe sa iam-test -n workshop
# TEst
aws s3 mb s3://eksworkshop-$ACCOUNT_ID-$AWS_REGION --region $AWS_REGION
# Create a pod that can run s3 ls command. In order to test that this pod can access S3
mkdir ~/environment/irsa

cat <<EoF> ~/environment/irsa/job-s3.yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: eks-iam-test-s3
  namespace: workshop
spec:
  template:
    metadata:
      labels:
        app: eks-iam-test-s3
    spec:
      serviceAccountName: iam-test
      containers:
      - name: eks-iam-test
        image: amazon/aws-cli:latest
        args: ["s3", "ls"]
      restartPolicy: Never
EoF

kubectl apply -f ~/environment/irsa/job-s3.yaml
# TEst. See that it completed   
kubectl get job -l app=eks-iam-test-s3 -n workshop
# Logs
kubectl logs -l app=eks-iam-test-s3 -n workshop
# TEst failure. Check pod that tries to run describe ec2 but we hae no role for this
cat <<EoF> ~/environment/irsa/job-ec2.yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: eks-iam-test-ec2
  namespace: workshop
spec:
  template:
    metadata:
      labels:
        app: eks-iam-test-ec2
    spec:
      serviceAccountName: iam-test
      containers:
      - name: eks-iam-test
        image: amazon/aws-cli:latest
        args: ["ec2", "describe-instances", "--region", "${AWS_REGION}"]
      restartPolicy: Never
  backoffLimit: 0
EoF

kubectl apply -f ~/environment/irsa/job-ec2.yaml
# Note it never completes
kubectl get job -l app=eks-iam-test-ec2 -n workshop
kubectl logs -l app=eks-iam-test-ec2 -n workshop

# Cleanup
kubectl delete -f ~/environment/irsa/job-s3.yaml
kubectl delete -f ~/environment/irsa/job-ec2.yaml

eksctl delete iamserviceaccount \
    --name iam-test \
    --namespace workshop \
    --cluster eksworkshop-eksctl \
    --wait

rm -rf ~/environment/irsa/

aws s3 rb s3://eksworkshop-$ACCOUNT_ID-$AWS_REGION --region $AWS_REGION --force
```
# HealthCheck
liveness (up but not fully ready) and readiness (ready for traffic)
readiness ready for traffic . Can set delay before kube starts polling them

## Lab

```
kubectl describe pod 
# Use this to see if pods status, incuding if they are restarting
export BE_POD_NAME=$(kubectl get pods -n workshop -l app=proddetail -o jsonpath='{.items[].metadata.name}') 
kubectl describe pod $BE_POD_NAME -n workshop
# See deploment desc
kubectl describe deployment proddetail -n workshop | grep Replicas: 

```

# Observability

- Cloudwatch container Insights
Gives axces to CPU/ memory utilizt=ation, Network Rx Rx, container instance clusters

*FluentBit* opensource log processor and forwarder


```
# Create role for Cloudwatch
eksctl create iamserviceaccount \
  --cluster eksworkshop-eksctl \
  --namespace amazon-cloudwatch \
  --name cloudwatch-agent \
  --attach-policy-arn  arn:aws:iam::aws:policy/CloudWatchAgentServerPolicy \
  --override-existing-serviceaccounts \
  --approve
# Now, Deploy Container Insights in the EKS cluster
ClusterName=eksworkshop-eksctl
FluentBitHttpPort='2020'
FluentBitReadFromHead='Off'
[[ ${FluentBitReadFromHead} = 'On' ]] && FluentBitReadFromTail='Off'|| FluentBitReadFromTail='On'
[[ -z ${FluentBitHttpPort} ]] && FluentBitHttpServer='Off' || FluentBitHttpServer='On'
curl https://raw.githubusercontent.com/aws-samples/amazon-cloudwatch-container-insights/latest/k8s-deployment-manifest-templates/deployment-mode/daemonset/container-insights-monitoring/quickstart/cwagent-fluent-bit-quickstart.yaml | sed 's/{{cluster_name}}/'${ClusterName}'/;s/{{region_name}}/'${AWS_REGION}'/;s/{{http_server_toggle}}/"'${FluentBitHttpServer}'"/;s/{{http_server_port}}/"'${FluentBitHttpPort}'"/;s/{{read_from_head}}/"'${FluentBitReadFromHead}'"/;s/{{read_from_tail}}/"'${FluentBitReadFromTail}'"/' | kubectl apply -f - 

`# Verofy. Expect to see cloudwatch and fluentBit groups
kubectl -n amazon-cloudwatch get daemonsets

# Create iam role for prometheus
eksctl create iamserviceaccount \
  --cluster eksworkshop-eksctl \
  --namespace amazon-cloudwatch \
  --name cwagent-prometheus \
  --attach-policy-arn  arn:aws:iam::aws:policy/CloudWatchAgentServerPolicy \
  --override-existing-serviceaccounts \
  --approve


```


# Autscaling
- Pod level / Horizonal scaling.. HPA Horiz Pod Autoscaler
- Node level/ Vertical

CAn define metric eg. memory or CPU with threshold, and scale if exceed.

```
# Create MEtric server
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
# Test
kubectl get apiservice v1beta1.metrics.k8s.io -o json | jq '.status'
# Creste HPA if cpu > 40
kubectl autoscale deployment proddetail -n workshop `#The target average CPU utilization` \
    --cpu-percent=40 \
    --min=1 `#The lower limit for the number of pods that can be set by the autoscaler` \
    --max=3 `#The upper limit for the number of pods that can be set by the autoscaler`
# test. See target updated to include HPA threshold of 40%
kubectl get hpa -n workshop
# Gen load in diff terminal
kubectl run -i --tty load-generator --image=busybox /bin/sh
while true; do wget -q -O - http://proddetail.workshop.svc.cluster.local:3000/catalogDetail; done
# AFter time this increases load and 2nd replca gets created
kubectl get hpa -n workshop -w
# lookup in console Pod Utilization Over Pod Limit
#In the AWS Console, go to CloudWatch -> Insights -> Container Insights and search for proddetail in the Resources list. There should be 2 results; click on proddetail of type EKS Pod.

### Karpenter. Replaces Cluster Autoscaler
export CLUSTER_NAME=$(eksctl get clusters -o json | jq -r '.[0].Name')
kubectl -n amazon-cloudwatch delete daemonsets cloudwatch-agent fluent-bit
eksctl delete iamserviceaccount --cluster ${CLUSTER_NAME}  --name cloudwatch-agent  --namespace amazon-cloudwatch
eksctl delete iamserviceaccount --cluster ${CLUSTER_NAME}  --name fluent-bit  --namespace amazon-cloudwatch

cd ~/environment/eks-app-mesh-polyglot-demo/workshop/
kubectl delete -f prometheus-eks.yaml
eksctl delete iamserviceaccount --cluster ${CLUSTER_NAME} --name cwagent-prometheus  --namespace amazon-cloudwatch

cd ~/environment/eks-app-mesh-polyglot-demo/workshop/
kubectl delete -f xray-eks.yaml
eksctl delete iamserviceaccount --cluster ${CLUSTER_NAME}  --name xray-daemon  --namespace default

# Eks Node Viewer
go install github.com/awslabs/eks-node-viewer/cmd/eks-node-viewer@latest
sudo mv -v ~/go/bin/eks-node-viewer /usr/local/bin
# Wait. THen when done run this in other terminal
eks-node-viewer
# Setup Karpenter
export CLUSTER_NAME=$(eksctl get clusters -o json | jq -r '.[0].Name')
export AWS_REGION=$(curl -s 169.254.169.254/latest/dynamic/instance-identity/document | jq -r '.region')
export AWS_ACCOUNT_ID="$(aws sts get-caller-identity --query Account --output text)"
export CLUSTER_ENDPOINT="$(aws eks describe-cluster --name ${CLUSTER_NAME} --query "cluster.endpoint" --output text)"

echo Cluster Name:$CLUSTER_NAME AWS Region:$AWS_REGION Account ID:$AWS_ACCOUNT_ID Cluster Endpoint:$CLUSTER_ENDPOINT
# Crete IAM role and Profile from karpenter, using cloudformation stack
TEMPOUT=$(mktemp)

curl -fsSL https://karpenter.sh/docs/getting-started/getting-started-with-karpenter/cloudformation.yaml  > $TEMPOUT \
&& aws cloudformation deploy \
  --stack-name "Karpenter-${CLUSTER_NAME}" \
  --template-file "${TEMPOUT}" \
  --capabilities CAPABILITY_NAMED_IAM \
  --parameter-overrides "ClusterName=${CLUSTER_NAME}"
# Add karpenter role to configMap
eksctl create iamidentitymapping \
  --username system:node:{{EC2PrivateDNSName}} \
  --cluster "${CLUSTER_NAME}" \
  --arn "arn:aws:iam::${AWS_ACCOUNT_ID}:role/KarpenterNodeRole-${CLUSTER_NAME}" \
  --group system:bootstrappers \
  --group system:nodes
# Test
kubectl describe configmap -n kube-system aws-auth
#crete iam role
eksctl utils associate-iam-oidc-provider --cluster ${CLUSTER_NAME} --approve

# create k8s service account and iam role
eksctl create iamserviceaccount \
  --cluster "${CLUSTER_NAME}" --name karpenter --namespace karpenter \
  --role-name "${CLUSTER_NAME}-karpenter" \
  --attach-policy-arn "arn:aws:iam::${AWS_ACCOUNT_ID}:policy/KarpenterControllerPolicy-${CLUSTER_NAME}" \
  --role-only \
  --approve

export KARPENTER_IAM_ROLE_ARN="arn:aws:iam::${AWS_ACCOUNT_ID}:role/${CLUSTER_NAME}-karpenter"
# Set version https://github.com/aws/karpenter/releases
 export KARPENTER_VERSION=v0.29.2
# Run
echo Your Karpenter version is: $KARPENTER_VERSION
docker logout public.ecr.aws
helm upgrade --install karpenter oci://public.ecr.aws/karpenter/karpenter --version ${KARPENTER_VERSION} --namespace karpenter --create-namespace \
  --set serviceAccount.annotations."eks\.amazonaws\.com/role-arn"=${KARPENTER_IAM_ROLE_ARN} \
  --set settings.aws.clusterName=${CLUSTER_NAME} \
  --set settings.aws.clusterEndpoint=${CLUSTER_ENDPOINT} \
  --set settings.aws.defaultInstanceProfile=KarpenterNodeInstanceProfile-${CLUSTER_NAME} \
  --set settings.aws.interruptionQueueName=${CLUSTER_NAME} \
  --wait

  
# Install Provisioner
# Karpenter configuration comes in the form of a Provisioner CRD (Custom Resource Definition) and defines a Custom Resource called a Provisioner to specify provisioning configuration. Provisioner defines how Karpenter manages unschedulable pods and expires nodes.  A cluster may have more than one Provisioner, but for the moment we will declare just one the default Provisioner . The provisioner can use Kubernetes labels to allow pods to request only certain instance types, architectures, operating systems, or other attributes when creating nodes. We can also [Deprovisioned]((https://karpenter.sh/docs/concepts/deprovisioning/ ) our nodes using karpenter by setting time-to-live value after a set amount of time from when teh nodes were created or after they dont have any deployed pods
cat <<EOF | kubectl apply -f -
apiVersion: karpenter.sh/v1alpha5
kind: Provisioner
metadata:
  name: default
spec:
  # References cloud provider-specific custom resource, see your cloud provider specific documentation
  providerRef:
    name: default
  ttlSecondsAfterEmpty: 30

  # Labels are arbitrary key-values that are applied to all nodes
  labels:
    eks-immersion-team: my-team

  # Requirements that constrain the parameters of provisioned nodes.
  # These requirements are combined with pod.spec.affinity.nodeAffinity rules.
  # Operators { In, NotIn } are supported to enable including or excluding values
  requirements:
    - key: "karpenter.k8s.aws/instance-category"
      operator: In
      values: ["c", "m"]
    - key: "kubernetes.io/arch"
      operator: In
      values: ["amd64"]
    - key: "karpenter.sh/capacity-type" # If not included, the webhook for the AWS cloud provider will default to on-demand
      operator: In
      values: ["on-demand"]
  limits:
    resources:
      cpu: "5"
    
  # Enables consolidation which attempts to reduce cluster cost by both removing un-needed nodes and down-sizing those
  # that can't be removed.  Mutually exclusive with the ttlSecondsAfterEmpty parameter.
  consolidation:
    enabled: false
---
apiVersion: karpenter.k8s.aws/v1alpha1
kind: AWSNodeTemplate
metadata:
  name: default
spec:
  subnetSelector:
      alpha.eksctl.io/cluster-name: ${CLUSTER_NAME}
  securityGroupSelector:
      alpha.eksctl.io/cluster-name: ${CLUSTER_NAME} 
  tags:
    managed-by: "karpenter"
    intent: "apps"         
EOF

# SCale
kubectl scale --replicas=30 deployment/proddetail -n workshop

# 30 nodes get rolled out quickly
kubectl get deployment/proddetail -n workshop
# Look at pods
kubectl get pods -l app=proddetail -n workshop -o wide --watch
# logs
kubectl logs -f deployment/karpenter -c controller -n karpenter

# KGN to see nodes (not pods)
kgn
 # Check eks-node-viewer to see the nodes and the pods per node
# scale down
kubectl scale --replicas=1 deployment/proddetail -n workshop


```

# K8s storage
If you have stateful services or microservices that share state.
You need persistent storage that can be shared
- StatefulSets
- Storage Clases
- PErsistent Volume
- Persistent volume claim

```
# Create EFS
CLUSTER_NAME=eksworkshop-eksctl
VPC_ID=$(aws eks describe-cluster --name $CLUSTER_NAME --query "cluster.resourcesVpcConfig.vpcId" --output text)
CIDR_BLOCK=$(aws ec2 describe-vpcs --vpc-ids $VPC_ID --query "Vpcs[].CidrBlock" --output text)
# Create security block
MOUNT_TARGET_GROUP_NAME="eks-efs-group"
MOUNT_TARGET_GROUP_DESC="NFS access to EFS from EKS worker nodes"
MOUNT_TARGET_GROUP_ID=$(aws ec2 create-security-group --group-name $MOUNT_TARGET_GROUP_NAME --description "$MOUNT_TARGET_GROUP_DESC" --vpc-id $VPC_ID | jq --raw-output '.GroupId')
aws ec2 authorize-security-group-ingress --group-id $MOUNT_TARGET_GROUP_ID --protocol tcp --port 2049 --cidr $CIDR_BLOCK
# check lifecycle status
aws efs describe-file-systems --file-system-id $FILE_SYSTEM_ID

# The EKS cluster that you created comprises worker nodes that are resident in the public subnets of the cluster VPC. Each public subnet resides in a different Availability Zone. As mentioned earlier, worker nodes connect to an EFS file system by using a mount target. It is best to create a mount target in each of the EKS cluster VPC's Availability Zones so that worker nodes across your EKS cluster can all have access to the file system.
TAG1=tag\:alpha.eksctl.io/cluster-name
TAG2=tag\:kubernetes.io/role/elb
subnets=($(aws ec2 describe-subnets --filters "Name=$TAG1,Values=$CLUSTER_NAME" "Name=$TAG2,Values=1" | jq --raw-output '.Subnets[].SubnetId'))
for subnet in ${subnets[@]}
do
    echo "creating mount target in " $subnet
    aws efs create-mount-target --file-system-id $FILE_SYSTEM_ID --subnet-id $subnet --security-groups $MOUNT_TARGET_GROUP_ID
done
# CAn verify in colsole, under netwrok for the EFS

# Install EFS CSI helm driver that helps provision efs mount points
helm repo add aws-efs-csi-driver https://kubernetes-sigs.github.io/aws-efs-csi-driver/
helm repo update
helm upgrade --install aws-efs-csi-driver --namespace kube-system aws-efs-csi-driver/aws-efs-csi-driver
# USe this file to provision
efs-pvc.yaml
cat: invalid option -- 'i'
Try 'cat --help' for more information.
WSParticipantRole:~/environment/eks-app-mesh-polyglot-demo (master) $ cat ~/environment/eks-app-mesh-polyglot-demo/workshop/efs-pvc.yamlkind: StorageClass
apiVersion: storage.k8s.io/v1
metadata:
  name: efs-sc
provisioner: efs.csi.aws.com

---
apiVersion: v1
kind: PersistentVolume
metadata:
  name: efs-pvc
spec:
  capacity:
    storage: 5Gi
  volumeMode: Filesystem
  accessModes:
    - ReadWriteMany
  persistentVolumeReclaimPolicy: Retain
  storageClassName: efs-sc
  csi:
    driver: efs.csi.aws.com
    volumeHandle: fs-016fb20a694b92984

---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: efs-storage-claim
  namespace: workshop
spec:
  accessModes:
    - ReadWriteMany
  storageClassName: efs-sc
  resources:
    requests:
      storage: 5Gi
# Run sed on that file
sed -i "s/EFS_VOLUME_ID/$FILE_SYSTEM_ID/g" ~/environment/eks-app-mesh-polyglot-demo/workshop/efs-pvc.yaml
# Apply
kubectl apply -f ~/environment/eks-app-mesh-polyglot-demo/workshop/efs-pvc.yaml
# look at Persistent vol claim (PVC)
kubectl get pvc -n workshop
# check pv that get created
kubectl get pv
#dEPLOY sTATEFUL SET. /using previouslt created efs yaml
cd ~/environment/eks-app-mesh-polyglot-demo
helm upgrade --reuse-values -f ~/environment/eks-app-mesh-polyglot-demo/workshop/helm-chart/values-efs.yaml workshop workshop/helm-chart/
# cat ~/environment/eks-app-mesh-polyglot-demo/workshop/helm-chart/values-efs.yaml
catalog:
  volume:
    enabled: true
    name: "efs-pvc"
    path: "/products"
    claim: "efs-storage-claim"
    
  image:
    tag: "3.6"

# verify
export PROD_CATALOG=$(kubectl get pods -n workshop -l app=prodcatalog -o jsonpath='{.items[].metadata.name}') 
kubectl -n workshop describe pod  ${PROD_CATALOG}
#
```

# Kubernetes Secrets and AWS SEcrets Manager
See google drive for explanation

We can store secrets int he AWS secrets manager, and then create a poicy to access it. 
- Create a secret with the AWS Secrets Manager.
- Create an IAM policy to retrieve a secret from the AWS Secrets Manager.
- Use IRSA to limit secret access to your pods in a specific namespace.
- Create and deploy SecretProviderClass custom resource and by using provider: aws
- Deploy your pods to mount the volumes based on SecretProviderClass configured earlier.
- Sync your secrets from mounted volumes to the native Kubernetes secrets object.
- Set up Environment variables in the pod, by selecting a specific key of your secret.

# Kubecost
