# Notes from O'Reilly CKAD course

https://github.com/bmuschko/ckad-prep

# Intro
2 hour exam
Details on certification : https://www.cncf.io/certification/ckad
Cheat sheet : https://kubernetes.io/docs/reference/kubectl/cheatsheet/

## The curriculum
13% Core concepts
* Understand api
* Create and configure basic pods

10% multi container pods

* Understand multi container pod desgin patterns
..* Ambasador
..* Adapter
..* Sidecar

13% SERvice and Networking

* Understand srevices
* Network policies

20% Pod design

* Understand labels selectors and annotators
* Understand deployments and how to perform rolling updates
* Rollbacks
* Jobs and cronJobs

18% Configuration

* Understan Config maps
SecrurityContexts
Define application resource erquirements
Understand ServiceAccounts

18% Observability

* Liveness and Readiness probes
* Understand container logging
* Understand monitoring
* Understand debugging

8% STate Persistence

* Understand PersistentVolumeClaims for storage

## Exam environemnt
Can take from home
Can access  https://kubernetes.io/docs only
2 hours to solve 19 problems
Keep moving though questions. Can skip 2 questions and still pass

### Timesaves
* Use alias  for kubectl
> alias k=kubectl
* Make sure to execute command question asks
* Perhaps create bash commands for bigger commands
> kubectl config set-context $1 --namespace=$2
* Use shortform commands e.g. instead of kubectl get namespaces 
> k get ns  
> k describe pvc claim  # Instead of persistentvolumeclaim

* Don't wait for graceful deltion of objects
> k delete pod nginx --grace-period=0 --force

* Grep for parsing information
> k describe pods | grep -C 10 "Authoer=John Doe"
> k get pods -o yaml | grep -C 5 labels:

### Bash
* Be comfortable with bash. Won't be able to look it up in exam
* Practise stuff
> if [ ! -d ~/tmp ]; then mkdir -p ~/tmp; fi; while true; 
> do echo $(date) >> ~/tmp/date.txt; sleep 5; done;

# Core Concepts

## Kubenetes Object
* API Version (change for breaking changes)
* Kind : Pod/ deployment / quota
* MetaData  : Name, Namespace, Labels
* Spec : Desired State.. How many containers etc
* Status : Actual state   .. Populated when running

**Yaml equivilent **
```
apiVesrion: v1
kind: Pod
metadata:
  createionTimestamp: null
  labels:
    run: nginx
  name: nginx
spec:
  containers:
  - image: nginx
    name: nginx
    resources: {}
  dnsPolicy: ClusterFirst
  restartPolicy: Never
status: {}
```  

* When creating kubernetes resources yo uhave 2 / 3 options
1. Command line  (note this means no history of changes. .Commands are run directly)
> kubectl create namespace nkad
> kubectl run nginx --image=nginx --restart=Never -n ckad
> kubectl edit pod/nginx -n ckad

1. Yaml
> vi ckad.yaml
> kubectl create -f ckad.yaml
> dubectl delete pod/nginx

1. Hybrid (use cmd to generate yaml)
> kubectl run nginx --image=nginx --restart=Never --dry-run -o yaml > nginx-pod.yaml
> vi nginx-pod.yaml
> kubectl apply -f nginx-pod.yaml

## Understanding Pods
* Single container Pod / Multi container Pod


### Kubernetes Master has 
* Api Server 
* etcd (db)  . Queried by controller and scheduler.
* scheduler
* Scheduler call kubectl
* talks to pod

### Pod Lifecycle
* Pending: Pod has been accepted in kubernetes but all of the container images have not been created
* Running : At least one container is still running
* Succeeded  : all containers in Pod finished  successfully
* Failed : at least containers in Pod finished with error
* Unknown

### Inspect Pod
* Get status
> kubectl describe pods nginx | grep Status: 

Status Running 

> kubectl get pods nginx -o yaml
... phase : Running

### Configuring Env variables
```
apiVersion: v1
kind: Pod
metadata:
  name: spring-boot-app
spec:
  containers:
  - image: 
  env :
  - name: SPRING_PORFILES_ACTIVE
    value: production
```

### Running commands inside container
```
apiVersion: v1
kind: Pod
metadata:
  name: spring-boot-app
spec:
  containers:
  - image: nginx:1.15.12
    name: nginx
  args:
  - /bin/sh
  - -c
  - echo "Hello World"
```

### Logs
* Dump the pods logs
> kubectl logs busybox
* Connet to a running pod
kubestl exec nginx -it -- /bin/sh   # creates inteactive session on a pod
