# Mstuff
alias python=python3
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # This loads nvm bash_completion
## K8s
KUSR=dunno
PWRD=pass
KREGION=eu-west-1
ALIAS=testK8
KNS=testK8
alias k=kubectl
PATH=$PATH:/home/keith/.local/bin/:$NVM_DIR:$HOME/kconnect
# load kunctl completions
source <(kubectl completion bash)
source <(helm completion bash)
#Alternative is one time (each for k, helm)
#> helm completion bash > /etc/bash_completions/helm
# Also do completions for k
complete -F __start_kubectl k
alias kall="k get all -o wide"
alias kgall="kubectl api-resources --verbs=list --namespaced -o name | xargs -n 1 kubectl get --show-kind --ignore-n    ot-found -l <label>=<value> -n $KNS < get all -o wide"
alias kc="konnect use eks --username $KUSR --password $PWRD --region $KREGION --no-input --role-arn arn:aws:iam::NuM    :role/EKS_NS_AP"
alias kvp="kc && kconnect to $ALIAS --password $PWRD && k config set-context --current --namespace=$KNS"