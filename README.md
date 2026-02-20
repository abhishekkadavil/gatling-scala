A boilerplate framework that helps you to write performance tests for E2E API using Gatling Scala

# Why/Why not
* **Why EC2 with ECS why not Fargate:**  
  Fargate is ideal for stateless, containerized microservices, not coordinated test runners. ECS on EC2 provides more control over the environment, making it suitable for performance testing scenarios that require specific configurations and resource management.
* why not use gatling to combine results of multiple simulations:  
  Gatling's built-in result combination is limited and may not handle complex scenarios well. Using external tools like Grafana and InfluxDB provides more flexibility and better visualization options for analyzing performance test results. Earlier gatling versions were supporting this feature but in latest versions this feature is removed.
* InfluxDB3:  
Given your requirements for a cost-effective and production-grade solution for Gatling distributed load testing report generation, InfluxDB 3 Core appears to be a suitable choice. Its high write throughput, efficient storage compression, and fast query performance align well with the demands of load testing scenarios.However, it's important to note that InfluxDB 3 Core is designed for single-node deployments and may not be ideal for scenarios requiring high availability or long-term data retention. If your use case involves such requirements, you might consider exploring InfluxDB 3 Enterprise or other TSDBs that offer these capabilities.

# Execute test
## From local machine
execute test: mvn gatling:test -Dgatling.simulationClass=simulations.TestChainingAPISimulations

# Infra architecture - Till test execution
                          ┌────────────────────────────┐
                          │   Jenkins Controller (UI)  │
                          │ - Web UI                   │
                          │ - Job scheduler/orchestrator│
                          └──────────┬─────────────────┘
                                     │
                                     │ SSH, JNLP, or EC2 Plugin
                                     ▼
                         ┌───────────────────────────────┐
                         │      Jenkins Build Agent       │
                         │ - Clones code                  │
                         │ - Prepares test artifacts      │
                         │ - Triggers remote Gatling runs │
                         └──────────┬────────────────────┘
                                     │
                                     │ SSH/SCP/Trigger scripts
                                     ▼
     ┌────────────────────┐   ┌────────────────────┐   ┌────────────────────┐
     │ Test EC2 Instance 1│   │ Test EC2 Instance 2│...│ Test EC2 Instance N│
     │ - Java, Maven      │   │ - Java, Maven      │   │ - Java, Maven      │
     │ - Gatling installed│   │ - Gatling installed│   │ - Gatling installed│
     │ - Runs actual test │   │ - Runs actual test │   │ - Runs actual test │
     └────────────────────┘   └────────────────────┘   └────────────────────┘

After the execution we can use InfluxDB and Grafana to visualize the results.

# Infra architecture - After test execution
    +----------------+             +-----------------+
    | Gatling EC2 #1 |  --->       |                 |
    | Gatling EC2 #2 |  --->       |  InfluxDB 1.8   |
    | Gatling EC2 #3 |  --->       |                 |
    +----------------+             +-----------------+
                                            |
                                            v
                                      +-------------+
                                      |   Grafana   |
                                      | Dashboards  |
                                      +-------------+


    [ Gatling EC2 ]  --->  [ Temporary EC2: InfluxDB + Grafana ]
        |                     |
        | (sends metrics)     |
        |-------------------->|
        |  run test           |
        |                     |
        |<----view results----|
terminate EC2s when done


# InfluxDB and Grafana Setup
* Setup InfluxDB 1.8 in local using docker
* Setup Grafana in local using docker
  * Configure InfluxDB datasource in grafana


# AWS Setup
Create AWS account
* First create account in AWS(root user)
  * Generate access key and secret for root user and save it in csv - used in jenkins global credentials(eg: AWS-Creds), after this will be used in our pipeline code to access aws resources using aws cli or sdk.
* Create IAM user with AmazonEC2FullAccess eg: jenkins-perf
  * Generate .pem file for that user and download it. Put it jenkins agent workspace(eg: /home/abhishek/jenkins-agent/jenkins-perf-key.pem) This is needed to access ec2 instance using ssh from local or jenkins, through code it's not needed.
* Create security group:  
  Create new security group in EC2 dashboard of ap-south-1 region(mumbai) of IAM user(jenkins-perf) - i didn't create new one and currently using default since its open for all traffic. But I added inbound rule for ssh port 22 to access EC2 instance using ssh from local.
* AMI Setup - Java/Maven/Git setup on EC2 (**please note this will increase cost since AMI need storage**):  
Installing packages every time may slow down the test. Ideally, create a custom AMI with Java, Maven, and Git pre-installed. That way, each EC2 instance is ready to run Gatling immediately. This is not mandatory, but if we configure it will save execution time.

# Jenkins Setup
jenkins used from local using docker. 

## Plugins to be installed
* AWS Credentials - to manage AWS credentials in jenkins
* Pipeline: Stage View - to visualize pipeline stages
* HTML Publisher - to publish html reports
* Amazon EC2 - to manage EC2 instances from jenkins

Jenkins node setup:  
Instead of stuffing everything into Jenkins, people usually:
* Run Jenkins as the controller (UI + scheduling).
* Let jobs run on separate agents (containers or EC2s) that already have AWS CLI (or Gatling, Terraform, etc.) installed.

That way:
* Jenkins controller stays clean.
* Each build runs in a fresh environment with only what it needs.

To summarize, Jenkins controller is the brain, and agents are the workers. To implement this jenkins need node to execute the pipeline code, so I am using local machine as node, usually in prod it can be EC2 instance or any other server or containers.

Setup local machine as jenkins node:

Prerequisites:
* Install java in local machine
* Install git in local machine
* Install maven in local machine
* Install aws cli in local machine

Steps:
* Add access key and secret csv downloaded from IAM user of aws root user in jenkins global credentials(eg: AWS-Creds)
* Create new node in jenkins
  * Name: local-agent
  * Remote root directory: /home/abhishek/jenkins-agent (Better to create in root direct otherwise it will show for permission error)
  * Labels: aws-ec2
* Connect to the node though command from the node created  
**(Dashboard > Manage Jenkins > Nodes > local-agent)**  
eg: ``` curl -sO http://localhost:8080/jnlpJars/agent.jar;java -jar agent.jar -url http://localhost:8080/ -secret ewfwefewfwefewfwefwefwefwef -name "local-agent" -webSocket -workDir "/home/abhishek/jenkins-agent" ```
* Place the pem file in jenkins-agent folder created above(/home/abhishek/jenkins-agent) - This is needed to access ec2 instance using ssh from local, through code it's not needed.