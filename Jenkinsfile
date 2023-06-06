pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                bat 'mvn install'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                bat 'mvn test'
            }
        }
        stage('SonarQube') {
                    steps {
                        echo 'SonarQube..'
                        withSonarQubeEnv(credentialsId: 'f225455e-ea59-40fa-8af7-08176e86507a', installationName: 'My SonarQube Server') { // You can override the credential to be used
                        bat 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                    }
                }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}