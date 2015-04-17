#! /usr/bin/env bash

source /vagrant/provision/constants.sh
source /vagrant/provision/utils.sh

echo_heading "Update and install packages"
{
  sudo apt-get update -qq
  sudo apt-get -yqq install software-properties-common python-software-properties
  sudo add-apt-repository -y ppa:webupd8team/java
  sudo apt-get update -qq
} > /dev/null

# http://askubuntu.com/questions/190582/installing-java-automatically-with-silent-option
echo debconf shared/accepted-oracle-license-v1-1 select true | \
  sudo debconf-set-selections
echo debconf shared/accepted-oracle-license-v1-1 seen true | \
  sudo debconf-set-selections

echo "Installing Java 8 and ignoring stderr because it's noisy"
sudo apt-get -yqq install oracle-java8-installer &> /dev/null
{
  sudo apt-get -yqq install oracle-java8-set-default # sets env vars
  sudo apt-get -yqq install maven
} > /dev/null

echo_heading "Idempotently add stuff to .profile"
# If not already there, then append command to execute .profile_additions to .profile
if ! grep -q ".profile_additions" $VAGRANT_HOME/.profile; then
  echo "source $PROVISION_DIR/.profile_additions" >> $VAGRANT_HOME/.profile
fi

echo -e "\nFinished provisioning"
