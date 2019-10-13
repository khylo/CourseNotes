# libpod / podman
Podman provides the ability to run containers via the LibPod project. LibPod provides a library for applications looking to use the Container Pod concept popularized by Kubernetes. By using Podman, it's possible to use the same runtime for running containers locally.

People sometimes alias docker=podman since podman is ocmpatible but offeres more features.

See https://www.katacoda.com/courses/container-runtimes/getting-started-with-podman

The CLI for Podman is compatible with Docker (but different
```
podman --help
podman run -d --name http-noports katacoda/docker-http-server:latest
podman ps # Note docker ps is empty since they are different (unless aliased)
curl localhost # nothing exposed
podman run -d --name http -p 80:80 katacoda/docker-http-server:latest  ## mapping ports
curl localhost # returns something from 2nd pod

podman inspect http # return configuratrion of container
```

Using amIcontained script we can see where we are 
`wget -O amicontained https://github.com/jessfraz/amicontained/releases/download/v0.3.0/amicontained-linux-amd64; chmod +x amicontained; ./amicontained`

e.g. in alpine container
```# ./amicontained
Container Runtime: not-found
Has Namespaces:
        pid: true
        user: false
AppArmor Profile: unconfined
Capabilities:
        AMBIENT -> chown dac_override fowner fsetid kill setgid setuid setpcap net_bind_service net_raw sys_chroot mknod audit_write setfcap
        BOUNDING -> chown dac_override fowner fsetid kill setgid setuid setpcap net_bind_service net_raw sys_chroot mknod audit_write setfcap
Chroot (not pivot_root): false
Seccomp: filtering
```
in host
```
$ ./amicontained
Container Runtime: not-found
Has Namespaces:
        pid: false
        user: false
AppArmor Profile: unconfined
Capabilities:
        BOUNDING -> chown dac_override dac_read_search fowner fsetid kill setgid setuid setpcap linux_immutable net_bind_service net_broadcast net_admin net_raw ipc_lock ipc_owner sys_module sys_rawio sys_chroot sys_ptrace sys_pacct sys_admin sys_boot sys_nice sys_resource sys_time sys_tty_configmknod lease audit_write audit_control setfcap mac_override mac_admin syslog wake_alarm block_suspend audit_read
Chroot (not pivot_root): false
Seccomp: disabled
```
