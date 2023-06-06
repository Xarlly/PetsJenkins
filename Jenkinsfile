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
                        def mvn = tool 'Default Maven';
                        withSonarQubeEnv() {
                            bat "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=JenkinsPets -Dsonar.projectName='JenkinsPets'"
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