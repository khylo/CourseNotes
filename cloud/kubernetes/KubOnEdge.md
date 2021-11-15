See https://learning.edx.org/course/course-v1:LinuxFoundationX+LFS156x+2T2021/block-v1:LinuxFoundationX+LFS156x+2T2021+type@sequential+block@c01b6b0fabf045a0a047a31cfd798a2b/block-v1:LinuxFoundationX+LFS156x+2T2021+type@vertical+block@bfc92ac12e1c43ee9c1d58d3ee4e8a72

* Cncf
* Lf eve

* EdgeXFoundry

* Balena 
OS. uses Moby for scheduling

* Akri. 
So, it seems that Akri is less about managing edge Kubernetes clusters, and more about using Kubernetes as a conduit, and data plane for accessing remote devices such as GPUs and IP cameras.

* K3. 
Low powered kubernetes K3s can provide similar capabilities to EVE through other CNCF and cloud native projects, and, when built as part of a complete architecture, can resemble a complete system more like EdgeX Foundry. We chose K3s as the core of this course for its flexibility and suitability to constrained devices. It also offers a very low barrier to entry for learning and experimentation.

See https://news.ycombinator.com/item?id=18080390  for news on the start of K3s.. Created by chief scientist at rancher labs early kubernetes compeditor.  " 8-million line patch "

Another company mariantis have k0s

When comparing it with Kubernetes, K3s:

Exposes the same REST API and kubectl CLI.
Uses the same YAML files and API objects.
Uses the same architecture and components.
Uses the same SDKs and client components.
See https://k3s.io/images/how-it-works-k3s.svg 

Where it differs is primarily in bundling and in bootstrapping. Core components that run and manage containers on a host like containerd (container runtime) and runc are built directly into the K3s binary. So, rather than installing these tools separately, followed by K3s, everything required can be distributed in a single binary. The bootstrapping is simplified through the use of an HTTP tunnel, which allows the nodes and masters to communicate together.

K3s cut that memory requirement down to just 500MB per server and 50MB per agent. See
https://rancher.com/docs/k3s/latest/en/installation/installation-requirements/resource-profiling/

It is distributed as a single binary making installation easier.

Originally replaceetcd with Kine (Kine is not etcd) .. Kine is an etcdshim that translates etcd API to sqlite, Postgres, Mysql, and dqlite See https://github.com/k3s-io/kine 

By default K3s is not HA. If master node fails cluster fails (using Kine). However it is possible to use etcd now instead of Kine. This makes it HA again,, but maybe more compliated.

See this blog post for instructions on setting up
https://blog.alexellis.io/bare-metal-kubernetes-with-k3s/
