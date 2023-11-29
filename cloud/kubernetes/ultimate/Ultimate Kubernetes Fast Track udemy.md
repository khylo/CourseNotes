# Ultimate Kubernetes FAst TRack udemy

# Intro
##
Sites: 
playwithkubernetes: https://labs.play-with-k8s.com/
play with k8s class: https://training.play-with-kubernetes.com/
minikube

See https://github.com/addamstj/kubernetes-course

## kubectl
Sample commands
```
kubectl completion bash >>  ~/.bash_completion

kubectl run <container>   # runs container in a pod

kubectl cluster-info  # gt info on cluster
kubectl get nodes # gets info on nodes
kubectl get pods # gets info on pods
k get ingress
# Get all in NS
kubectl api-resources --verbs=list --namespaced -o name \
  | xargs -n 1 kubectl get --show-kind --ignore-not-found -l <label>=<value> -n <namespace>
kubectl describe pod
# Create inst
# kubectl run <name> --image <img>
kubextl run nginx --image nginx
kubectl create -f desc.yaml

# lookup versions
k api-resources | grep deploy 
```

## HELM
```
# List all (local)
helm ls --all
# Add repo to loca helm
helm repo add bitnami https://charts.bitnami.com/bitnami
# Sample to add internal repos
helm repo add artyPre https://artifactory.domain.com/helm-prereleases
helm repo add artyRel https://artifactory.domain.com/releases

## Search
# if you know the name
helm search repo <reponame> name
# to use regex
helm search repo -r ".*name.*" 
## Template (debug helm chart)
helm template 
## Sample
helm create helloworld
tree helloworld
helm template helloworld
helm install myHelloRelease helloworld
helm list -a
# Break it and show template will catch error
vi helloworld/template/deployment.yaml # delete kind, mess up indent
helm template helloworld
```

# Key Concepts

apps are deployd in containers.
* A *pod* is the minimal K8s concept it contains 1 (normally) or more containers
* A *replicaset* wraps a pod and controls how many instances of it are created
* A *deployment* wraps a replicaSet

## Networking
*CLuster* site on its own virtual network (normally 10.x.x.x)
Nodes, and pods get created in this range.

WHen creating a service note diff between port and target port.

Port is the external port  (e.g. 80), and targetPort is the internal port (e.g. 8080)


### Service
To connect to external entities we expose a service. 
THere are 4 types of service.
*ClusterIP* This  is an internal IP address.
*NodePort*, *LoadBalancer*, *Ingress*, these are entities for interfacing with external network from within the virtual network. Labels are matched.

* *NodePort* exposes a port on the node. Connects to a service (via label). This connects to pod. *Dont  use in Prod. Very insecure*
* *LoadBalancer* .. Like ELB
  Sits infront of Service, and Pods
* *Ingress*
  Has its own kind in yaml. Normally uses nginx
  Has a service 
  Can have sticky sessions
  Pass in nginx config so its like an internal loadbalancer

Kube-PRoxy. Handles all internal networking
Kube-DNS. Handles IP config in internal network

By DEfault services in a cluster follow this naming conventionFor,e.g. for  pod to pod communicatiON
mySrc.myNamespace.svc.cluster-domain.example
For example 
```
k get all
# Lookup service for ip details
k describe <serviceName>
# REturns Name, NAmespce, labels, annotations, Type (cluser / nodeport/ etc)
# IP Family, IP, IPs, Session Affinity, port, targetPort
```

### Namespace
Add namespaces using yaml (kind=Namespace) e.g.
```
apiVersion: v1
kind: Namespace
metadata:
  name:  myNS
```
k create -f ns.yaml

set default NS in .kubectl
or can do k get -n myNS
THen refer to this NS in the metadata of entities (e.g. pods) e.g.
```
metadata:
  namespae: myNS
  name: myapp
  labels:
    name: myapp
```

## Ingress
FIrst enable ingress on minikube
```
minikube addons enable ingress
```

Note for minikube, nginx is the default ingress controller

Can pass in annoations to nginx. See https://kubernetes.github.io/ingress-nginx/user-guide/basic-usage/ 
x
```
metadata:
  annoations:

```
# Yaml

List and dicts

```
# Below Got element is a list
# toGet is a Dict (since we have valuess)
# return is also a dict.. Not sure of diff 
shopping:
  got:
    - eggs
    - bread
    - milk
  toGet:
    - apples: 6
    - bananas: 6
  return:
    cheese: cheddar
    cream: 500ml
```

for pods use vscode plugin to generate template (type kind:  ctrl tab)

Note containers is a list as we can have multiple containers in a pod
```
apiVersion: v1
kind: Pod
metadata:
  name: myapp
  labels:
    name: myapp
spec:
  containers:
  - name: myapp
    image: <Image>
    resources:
      limits:
        memory: "128Mi"
        cpu: "500m"
    ports:
      - containerPort: <Port>

```