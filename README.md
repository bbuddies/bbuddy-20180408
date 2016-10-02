[![Build Status](https://travis-ci.org/nerds-odd-e/bbuddy.svg?branch=master)](https://travis-ci.org/nerds-odd-e/bbuddy)

This is the repo for the exercise project to be developed in Shanghai team's Modern Web Development course

# Installation
Please install the following tools for this project. The latest version should be fine unless specific version is listed.

* git
* jdk 1.8
* gradle 2.X (don't use gradle 3.X)
* mysql
* intellij idea community edition with the following plug-in installed
    * lombok plug-in
* Firefox 46.0 (don't use higher version)

# Setup Development Environment
Use git to clone this project into a folder. Then in this folder, run the command below in order.

* Start Web Application `SPRING_PROFILES_ACTIVE=dev gradle clean bootRun` (on port 8090)
    * if you want to start it on a port rather than 8090 (e.g. 8070), please do `SPRING_PROFILES_ACTIVE=dev SERVER_PORT=8070 gradle clean bootRun`
* Run All Tests `SPRING_PROFILES_ACTIVE=test gradle clean check cucumber` (on port 8080)

# Setup Development Environment using VM
Install the following tools first.

* [VirtualBox](https://www.virtualbox.org/)
* [Vagrant](https://www.vagrantup.com)
* [Ansible](https://www.ansible.com/)

Then run the command: `vagrant up`

# If you are using MacOS, and have [Homebrew](http://brew.sh/) installed.
Run the below commands:

    brew cask install virtualbox vagrant
    brew install ansible
    vagrant up
