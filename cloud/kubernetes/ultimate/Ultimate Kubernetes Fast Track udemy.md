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
kubectl describe pod
# Create inst
# kubectl run <name> --image <img>
kubextl run nginx --image nginx
kubectl create -f desc.yaml

```

# Key Concepts

apps are deployd in containers.
A *pod* is the minimal K8s concept it contains 1 (normally) or more containers
A *replicaset* wraps a pod and controls how many instances of it are created
A *deployment* wraps a replicaSet

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