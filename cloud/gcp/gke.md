# GKE
Google Kubernetes Engine

Create cluster 
Click on connect

#Configure kubectl command-line access by running the following command:
gcloud container clusters get-credentials consul-cluster --zone europe-west2-a --project consul-gke-xp


$ kubectl cluster-info
Kubernetes master is running at https://<your GKE ip(s)>
GLBCDefaultBackend is running at https://<your GKE ip(s)>/api/v1/namespaces/kube-system/services/default-http-backend:http/proxy
Heapster is running at https://<your GKE ip(s)>/api/v1/namespaces/kube-system/services/heapster/proxy
KubeDNS is running at https://<your GKE ip(s)>/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy
Metrics-server is running at https://<your GKE ip(s)>/api/v1/namespaces/kube-system/services/https:metrics-server:/proxy

To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'.
