#  Certified K8S App Developer 
See udemy.com
*CNCF course signup:* https://www.cncf.io/certification/ckad/
Use Code : 20KLOUD  to get 20% discount from linux foundation.

*Handbook*: https://docs.linuxfoundation.org/tc-docs/certification/lf-handbook2 

*Exam Tips:* https://docs.linuxfoundation.org/tc-docs/certification/tips-cka-and-ckad

# Basics
* Node
   Worker machine / minion
* Pods
smallest obj in k8s.. abstration of single inst of applications. K8s scales pods and nodes. Can have helper containers running with app container in same pod .. then can communication over localhost. 
kubectl run .. creates and run a container in a pod
* Cluster
   Group of nodes for perf/ failsafe
* Master
   Watches nodes and orchestrates

# Components
* Api Server
  Front end / CLI etc. Runs on master
* etcd
   DIstribed keystore.. lie DB.. runs on master??
* kubelet
agent
* Container Runtime
Linux / docker
* Controller
Orchestraiton.. Brings up new containers
* Scheduler

# yamml
| Kind         | Version |
|--------------|---------|
| POD          | v1      |
| Service      | v1      |
| ReplicateSet | apps/v1 |
| Deployment   | apps/v1 |

## Pods
kubectl get pods
ubectl describe <pod>
A pod contains these main things
* apiVersion. 
  Verseion of k8s.. Can mean differnent entities 
  v1 = pod or service
  app/v1 = ReplicaSet or Deployment
* kind
 type of obj, See table above
* metadata
    Dictionary (Key / values). Can be anything. this is an ex.
    name:  <name>
    labels:
        app: <myApp>
        type: frontEnd
* Spec
Specifiction. Information like pods, nodes, clusters etc
For pod
spec:
  xontainers:
    - name: nginx-container
      image: nginx

Node

# kubectl
Sample commands

kubectl run <container>   # runs container in a pod

kubectl cluster-info  # gt info on cluster
kubectl get nodes # gets info on nodes
kubectl get pods # gets info on pods

# Docker vs containerd
cri = container runtime interface created by k8s to standardise container runtimes

Means different container runtimes supported with k8s
Backport of docker using dockershim to make it compatible

Sinxe K8s v1.24 dockershim removed, so now can use containerd instead.. But it is less user friendly, so recommend to nerdctl instead of basic ctl. It is a replacemnet for docker in many ways, with compose run etc all supported
See https://kiranchavala.in/blog/using-crictl-and-nerdctl/ 


* crictl
cli to interact with all CRI instnace from K8s
Debugging tool 

crictl pull busybox
crictl images
crictl exec -i -y <img>
crictl pods
crictl logs