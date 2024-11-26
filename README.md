# Jenkins Pipeline Documentation

This document provides an overview of the Jenkins pipeline configuration used for building, testing, and deploying the **Car Pooling Backend** application. The pipeline automates the **Continuous Integration (CI)** and **Continuous Delivery (CD)** processes.
## Table of Contents
1. [Overview](#overview)
2. [Continuous Integration (CI)](#continuous-integration-ci)
   1. [Checkout](#1-checkout)
   2. [Build Artifact](#2-build-artifact)
   3. [Quality Analysis](#3-quality-analysis)
   4. [Quality Gate](#4-quality-gate)
   5. [Login to Nexus](#5-login-to-nexus)
   6. [Build Docker Image](#6-build-docker-image)
   7. [Push Docker Image](#7-push-docker-image)
3. [Continuous Delivery (CD)](#continuous-delivery-cd)
    1. [.env file](#1-env-file)
    2. [Docker compose file](#2-docker-compose-file)
    3. [Deploy stage](#3-deploy-stage)
## Overview

This Jenkins pipeline automates the following key steps:
1. **Continuous Integration (CI)**: Code retrieval, building, testing, quality analysis, Docker image build, and push to Nexus. 
2. **Continuous Delivery (CD)**: Deployment via Docker Compose.

Project Structure:
```markdown
├── src  
├── .env
├── Dockerfile
├── docker-compose.yaml  
├── Jenkinsfile
├── mvnw.cmd  
├── pom.xml  
├── README.md  
└── mvnw
```

---

## Continuous Integration (CI)

The **Continuous Integration (CI)** part of the pipeline automates the build, testing, quality analysis process, building and pushing the docker image to nexus.

### 1. **Checkout**
The pipeline starts by checking out the source code from the **GitHub** repository `https://github.com/hjaijhoussem/Car-pooling-backend.git` using the provided Jenkins credentials (`github-jenkins`).

```groovy
stage('Checkout') {
    steps {
        git branch: 'main', credentialsId: 'github-jenkins', url: 'https://github.com/hjaijhoussem/Car-pooling-backend.git'
    }
}
```
### 2. **Build Artifact**
The `mvn clean install -DskipTests` command is executed to build the project, skipping the unit tests in this stage to speed up the build.
```groovy
stage('Build Artifact') {
    steps {
        sh 'mvn clean install -DskipTests'
    }
}
```
### 3. **Quality Analysis**
SonarQube analysis is performed to ensure code quality. The pipeline uses the `mysonar` configuration for SonarQube, with the analysis settings specified for the project.
```groovy
stage('Quality Analysis') {
    steps {
        withSonarQubeEnv('mysonar') {
            sh """
            mvn sonar:sonar \
                -Dsonar.projectKey=car-pooling \
                -Dsonar.projectName=car-pooling \
                -Dsonar.sources=src/main/java \
                -Dsonar.java.binaries=target/classes
            """
        }
    }
}
```
### 4. **Quality Gate**
The pipeline waits for the quality gate approval from SonarQube. If the quality gate fails, the pipeline will be aborted.
```groovy
stage('Quality Gate') {
    steps {
        waitForQualityGate abortPipeline: true
    }
}
```
### 5. **Login to Nexus**
The Jenkins pipeline logs into the Nexus Docker registry using the credentials stored in Jenkins (`nexus`).
```groovy
stage('Login to Nexus') {
    steps {
        script {
            withCredentials([usernamePassword(credentialsId: 'nexus', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                sh "docker login ${NEXUS_URL} -u ${NEXUS_USERNAME} -p ${NEXUS_PASSWORD}"
            }
        }
    }
}
```
### 6. **Build Docker Image**
The Docker image is built using the `docker build` command, tagged with the project version (`${BUILD_ID}`).
```groovy
stage('Build Docker Image') {
    steps {
        script {
            sh "docker build -t ${DOCKER_IMAGE_NAME} ."
        }
    }
}
```
### 7. **Push Docker Image**
The pipeline tags the Docker image and pushes it to the **Nexus** repository for later deployment.
```groovy
stage('Push Docker Image') {
    steps {
        script {
            sh "docker tag ${DOCKER_IMAGE_NAME} localhost:6666/repository/dockerhosted-repo/${NEXUS_REPO}/${DOCKER_IMAGE_NAME}"
            sh "docker push localhost:6666/repository/dockerhosted-repo/${NEXUS_REPO}/${DOCKER_IMAGE_NAME}"
        }
    }
}
```
---
## Continuous Delivery (CD)
The **Continuous Delivery (CD)** phase is managed by the **deploy stage**. Prior to deployment, the `BACKEND_IMAGE` variable is defined in the `.env` file and referenced within the `docker-compose.yaml` file. During the **deploy stage**, this variable is **overridden** and assigned the value of the newly created Docker image, which is tagged with the current `build ID`.
### 1. **.env file**
```dotenv
...
BACKEND_IMAGE=car-pooling-be
```
### 2. **Docker compose file**
```yaml
version: '3.3'
services:

  db:
    image: mysql:5.7  
    container_name: car-pooling-db
    environment:
      MYSQL_DATABASE: ${MYSQL_DATABASE}
      MYSQL_USER: ${MYSQL_USER}
      MYSQL_PASSWORD: ${MYSQL_PASSWORD}
    ports:
      - '3306:3306'
    networks:
      - car-pooling

  backend:
    # Injecting the BACKEND_IMAGE variable from the .env file as image name
    image: ${BACKEND_IMAGE} 
    container_name: car-pooling-be
    ports:
      - '8088:8088'
    networks:
      - car-pooling
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/${MYSQL_DATABASE}
      SPRING_DATASOURCE_USERNAME: ${MYSQL_USER}
      SPRING_DATASOURCE_PASSWORD: ${MYSQL_PASSWORD}
    depends_on:
      - db  

networks:
  car-pooling:
    driver: bridge
```
### 3. **Deploy stage**
```groovy
stage('Deploy') {
    environment {
        BACKEND_IMAGE = "${DOCKER_IMAGE_NAME}"  // Set the image tag to the current build ID
    }
    steps {
        input message: 'Approve Deployment',
            parameters: [
                choice(
                    name: 'Proceed with deployment?',
                    choices: ['Yes', 'No'],
                    description: 'Do you want to deploy the new image?'
                )
            ]
        sh 'docker compose up -d'
    }
}
```