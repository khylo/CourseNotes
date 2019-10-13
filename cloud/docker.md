# Containers
See https://www.katacoda.com/courses/container-runtimes 

## Commands
* unshare
allow seperate namespaces for procs
* nsenter
Share namespaces for procs
* Chroot 
An important part of a container process is the ability to have different files that are independent of the host. This is how we can have different Docker Image
Cgroups (Control Groups). 
CGroups limit the amount of resources a process can consume. These cgroups are values defined in particular files within the /proc directory.

```
docker run -d --name=db redis:alpine
ps aux | grep redis-server # See docker image runing on host
docker top ps # show top processes on docker image
# Note ppid = redis server parent porcess = containerd 
pstree -p -A ${pgrep dockerd)
```

## As files
Ultimately unix stores all setting as files so next commands demonstrate what docker is doing under the hood to interact with linux file data.
```
ls /proc/$(pgrep redis-server)  # show proc folder for this image
cat /proc/$(pgrep redis-server)/environ # show environ settings.. smae as next command
docker exec -it db env
```

Docker uses namesspaces to control what processes can see and access. Availabel namespaces are 
https://en.wikipedia.org/wiki/Linux_namespaces
* Mount (mnt)
* Process ID (pid)
* Network (net)
* Interprocess Communication (ipc)
* UTS (hostnames)
* User ID (user)
* Control group (cgroup)

```
sudo unshare --fork --pid --mount-proc bash  # unshare allows processes to run i ntheir own namespace. Docker uses this. Note mount-proc
ls -lha /proc/$(pgrep redis-server)/ns # show namespaces
# Under the covers, Namespaces are inode locations on disk. This allows for processes to shared/reused the same namespace, allowing them to view and interact.
nsenter --target $(pgrep redis-server) --mount --uts --ipc --net --pid ps -ef # attach process to existing namespaces
# With Docker, these namespaces can be shared using the syntax container:<container-name>. For example, the command below will connect nginx to the DB namespace.
docker run -d --name=web --net=container:db nginx:alpine
WEBPID=$(pgrep nginx | tail -n1)
echo nginx is $WEBPID
cat /proc/$WEBPID/cgroup
# Note both procs share the same net namespace
ls -lha /proc/$WEBPID/ns/ | grep net 
ls -lha /proc/$(pgrep redis-server)/ns/ | grep net
cat /proc/$DBPID/cgroup # show cgroups.. resource mgmt
cat /sys/fs/cgroup/cpu,cpuacct/docker/$DBID/cpuacct.stat # cpu stats and usage
cat /sys/fs/cgroup/cpu,cpuacct/docker/$DBID/cpu.shares # cpus share limit
#By default, containers have no limit on the memory. We can view this via docker stats command.
docker stats db --no-stream
cat /sys/fs/cgroup/memory/docker/$DBID/memory.limit_in_bytes # matches docker value
#By writing to the file, we can change the limit limits of a process.
echo 8000000 > /sys/fs/cgroup/memory/docker/$DBID/memory.limit_in_bytes
cat /sys/fs/cgroup/memory/docker/$DBID/memory.limit_in_bytes # value updated
docker stats db --no-stream # docker result updated too
```
### System call AppArmor
All actions with Linux is done via syscalls. The kernel has 330 system calls that perform operations such as read files, close handles and check access rights. All applications use a combination of these system calls to perform the required operations.

AppArmor is a application defined profile that describes which parts of the system a process can access.
When assigned to a process it means the process will be limited to a subset of the ability system calls. If it attempts to call a blocked system call is will recieve the error "Operation Not Allowed".

The status of SecComp is also defined within a file. When assigned to a process it means the process will be limited to a subset of the ability system calls. If it attempts to call a blocked system call is will recieve the error "Operation Not Allowed".

The status of SecComp is also defined within a file.

```
cat /proc/$DBPID/attr/current
cat /proc/$DBPID/status
cat /proc/$DBPID/status | grep Seccomp
```

### Capabilities
Capabilities are groupings about what a process or user has permission to do. These Capabilities might cover multiple system calls or actions, such as changing the system time or hostname.

The status file also containers the Capabilities flag. A process can drop as many Capabilities as possible to ensure it's secure.

` cat /proc/$DBPID/status | grep ^Cap `

The flags are stored as a bitmask that can be decoded with capsh

` capsh --decode=00000000a80425fb ` 

