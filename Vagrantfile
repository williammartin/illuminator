# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|

  config.vm.box = "hashicorp/precise64"

  config.vm.provision "shell", path: "./provision/bootstrap.sh", privileged: false

end
