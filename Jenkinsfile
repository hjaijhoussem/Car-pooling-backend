pipeline {
    // testing webhook again
    // load balancer -> Teamboost server + REDIS
    agent any

    tools {
        maven '3.9.5'
        dockerTool 'docker'
    }

    environment {
        // NEXUS_CREDENTIAL_ID = "nexus"
        // NEXUS_URL = 'localhost:6666'
        // NEXUS_REPO = 'dockerhosted-repo'
        DOCKER_IMAGE_NAME = "car-pooling-be:${BUILD_ID}"
        REGISTRY_URL = 'ghcr.io'
        REGISTRY_REPO = 'hjaijhoussem'

        JAR_FILE = 'target/*.jar'
        GITHUB_PKG_URL = "https://maven.pkg.github.com/hjaijhoussem/Car-pooling-backend"

    }
    stages{
        

        stage('Checkout'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], userRemoteConfigs: [[credentialsId: 'pipe-gh-token', url: 'git@github.com:hjaijhoussem/Car-pooling-backend.git']])
            }
        }

        stage('Build Artifact'){
        // when {
        //     anyOf{
        //         branch 'main'
        //         expression { return params.current_status == "opened" && params.merged == false && params.branch == "main"}
        //         //triggeredBy 'GitHubPullRequest'
        //     }
        // }
            steps{
                sh 'mvn clean install -DskipTests'
            }
        }

        // stage('Quality Analysis'){
        //     when {
        //         branch 'main'
        //     }
        //     steps{
        //         withSonarQubeEnv('mysonar'){
        //            sh """
        //             mvn sonar:sonar \
        //                 -Dsonar.projectKey=car-pooling \
        //                 -Dsonar.projectName=car-pooling \
        //                 -Dsonar.sources=src/main/java \
        //                 -Dsonar.java.binaries=target/classes
        //             """
        //         }

        //     }
        // }
        // stage('Quality Gate'){
        //     // when {
        //     //     branch 'main'
        //     // }
        //     steps{
        //         waitForQualityGate abortPipeline: true
        //     }
        // }

        stage('Login to Nexus') {

            // when {
            //     branch 'main'
            // }

            // steps {
            //     script {
            //         withCredentials([usernamePassword(credentialsId: 'nexus', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
            //             sh "docker login http://${NEXUS_URL} -u ${NEXUS_USERNAME} -p ${NEXUS_PASSWORD}"
            //         } 
            //     }
            // }
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'perso-gh-registry', usernameVariable: 'GH_USERNAME', passwordVariable: 'GH_PASSWORD')]) {
                        sh "docker login ${REGISTRY_URL} -u ${GH_USERNAME} -p ${GH_PASSWORD}"
                    } 
                }
            }
        }
        stage('Build Docker Image') {

            // when {
            //     branch 'main'
            // }

            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE_NAME} ."
                }
            }
        }
        stage('Push Docker Image') {

            // when {
            //     branch 'main'
            // }

            // steps {
            //     script {
            //         sh "docker tag ${DOCKER_IMAGE_NAME} ${NEXUS_URL}/repository/${NEXUS_REPO}/${DOCKER_IMAGE_NAME}"
            //         sh "docker push ${NEXUS_URL}/repository/${NEXUS_REPO}/${DOCKER_IMAGE_NAME}"
            //     }
            // }
            steps {
                script {
                    sh "docker tag ${DOCKER_IMAGE_NAME} ${REGISTRY_URL}/${REGISTRY_REPO}/${DOCKER_IMAGE_NAME}"
                    sh "docker push ${REGISTRY_URL}/${REGISTRY_REPO}/${DOCKER_IMAGE_NAME}"
                }
            }
        }

        stage('Login to GitHub Packages') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'perso-gh-registry', usernameVariable: 'GH_USERNAME', passwordVariable: 'GH_PASSWORD')]) {
                        // Create settings.xml for Maven
                        writeFile file: 'settings.xml', text: """
                            <settings>
                                <servers>
                                    <server>
                                        <id>github</id>
                                        <username>${GH_USERNAME}</username>
                                        <password>${GH_PASSWORD}</password>
                                    </server>
                                </servers>
                            </settings>
                        """
                    }
                }
            }
        }

        stage('Publish JAR') {
            steps {
                script {
                    sh """
                        mvn deploy:deploy-file \
                        -DgroupId=com.example \
                        -DartifactId=car-pooling-be \
                        -Dversion=${BUILD_ID} \
                        -Dpackaging=jar \
                        -Dfile=${JAR_FILE} \
                        -DrepositoryId=github \
                        -Durl=${GITHUB_PKG_URL} \
                        -s settings.xml
                    """
                }
            }
        }
        // stage('Deploy') {
        //     environment {
        //         BACKEND_IMAGE = "${DOCKER_IMAGE_NAME}"
        //     }
        //     // when {
        //     //     branch 'main'
        //     // }

        //     steps {
        //         input message: 'Approve Deployment',
        //           parameters: [
        //               choice(
        //                   name: 'Proceed with deployment?',
        //                   choices: ['Yes', 'No'],
        //                   description: 'Do you want to deploy the new image?'
        //               )
        //           ]
        //         sh 'docker compose up -d'
        //     }
        // }
    }

}
