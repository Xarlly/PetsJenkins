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
            }
        }
        stage('SonarQube') {
                    steps {
                        echo 'SonarQube..'
                    }
                }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}