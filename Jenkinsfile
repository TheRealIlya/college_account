pipeline {
    agent any
    tools {
        maven 'Maven-Default'
        jdk 'OpenJDK-11'
    }

    stages {
        stage('Clean previous version') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'SUCCESS') {
                    sh 'mvn clean'
                }
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn install -DskipTests=true'
            }
        }

        stage('SonarQube check') {
            steps {
                withSonarQubeEnv(credentialsId: 'sonar-tipa',
                installationName: 'local-sonarqube') {
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                }
            }
        }
    }
}
