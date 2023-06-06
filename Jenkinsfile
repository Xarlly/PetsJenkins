pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                bat 'mvn -DskipTests install'
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
                        withSonarQubeEnv('SonarQube') {
                            bat 'mvn sonar:sonar -Dsonar.token=sqp_0599cebbf73058e07b5dab8567d53f8253b369c8'
                        }
                    }
                }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}