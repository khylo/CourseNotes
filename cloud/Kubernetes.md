# Kubernetes

## Minikube
Can run minKube locally .see https://minikube.sigs.k8s.io/docs/start/windows/

Startup with 
`minikube start --wait=false`

When started you can use ``kubectl`` commands,

*STart service*
`kubectl create deployment first-deployment --image=katacoda/docker-http-server`

`kubectl get nodes` # get nodes info.. wait for nodes to be ready

`kubectl get pods` # get pods info only when doing something

```
export PORT=$(kubectl get svc first-deployment -o go-template='{{range.spec.ports}}{{if .nodePort}}{{.nodePort}}{{"\n"}}{{end}}{{end}}')
echo "Accessing host01:$PORT"
curl host01:$PORT
```

`minikube addons enable dashboard` # Enable minikube daskboard
`kubectl apply -f /opt/kubernetes-dashboard.yaml` # apply config to expose dashboard on port 30000
`kubectl get pods -n kube-system  -w` # watch pods status as dashboard starts up

## kubectl

```
kubectl get nodes # get nodes info.. wait for nodes to be ready

# run
kubectl create deployment first-deployment --image=katacoda/docker-http-server`
kubectl run http --image=katacoda/docker-http-server:latest --replicas=1 # start a cluster 1 replica
kubectl run httpexposed --image=katacoda/docker-http-server:latest --replicas=1 --port=80 --hostport=8001
#monitor pods
kubectl get pod` # get pods info only when doing something
kubectl get deployments # show deployments
kubectl describe deployment http # get lots of information deployments
# expose to open ports  e.g ports 80 -> 8000
kubectl expose deployment http --external-ip="172.17.0.77" --port=8000 --target-port=80
# scale
kubectl scale --replicas=3 deployment http
```
