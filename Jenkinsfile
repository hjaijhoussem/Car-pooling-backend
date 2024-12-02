pipeline {
    agent any
    tools {
        maven '3.9.5'
    }
    environment {
        NEXUS_CREDENTIAL_ID = "nexus"
        NEXUS_URL = 'localhost:6666'
        NEXUS_REPO = 'dockerhosted-repo'
        DOCKER_IMAGE_NAME = "car-pooling-be:${BUILD_ID}"
    }
    stages{

        stage('Build Artifact'){
            steps{
                sh 'mvn clean install -DskipTests'
            }
        }

        stage ('dev'){
            when {
                allOf{
                    changeRequest()
                    branch 'main'
                }
            }
            steps {
                echo "works only for dev branch"
            }
        }
        stage('Quality Analysis'){
            steps{
                withSonarQubeEnv('mysonar'){
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
        stage('Quality Gate'){
            steps{
                waitForQualityGate abortPipeline: true
            }
        }

        stage('Login to Nexus') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'nexus', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                        sh "docker login http://${NEXUS_URL} -u ${NEXUS_USERNAME} -p ${NEXUS_PASSWORD}"
                    } 
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE_NAME} ."
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    sh "docker tag ${DOCKER_IMAGE_NAME} ${NEXUS_URL}/repository/${NEXUS_REPO}/${DOCKER_IMAGE_NAME}"
                    sh "docker push ${NEXUS_URL}/repository/${NEXUS_REPO}/${DOCKER_IMAGE_NAME}"
                }
            }
        }
        stage('Deploy') {
            environment {
                BACKEND_IMAGE = "${DOCKER_IMAGE_NAME}"
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
    }
    post {
        always {
            echo 'Cleaning up...'
            sh 'mvn clean'
        }
        success {
            echo 'The build and SonarQube analysis were successful with a passing quality gate and unit test!'
        }
        failure {
            echo 'The build, SonarQube analysis, unit tests, or quality gate check failed.'
        }
    }
}
