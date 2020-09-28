# Notes from O'Reilly CKAD course


# Intro
2 hour exam
Details on certification : https://www.cncf.io/certification/ckad
Cheat sheet : https://kubernetes.io/docs/reference/kubectl/cheatsheet/

## THe curriculum
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



