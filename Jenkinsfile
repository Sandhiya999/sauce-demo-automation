pipeline {
    agent any

    tools {
        jdk 'temurin-11'
        maven 'Maven3'
    }

    environment {
        REPORT_DIR = 'reports'
        SCREENSHOT_DIR = 'reports/screenshots'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test -DsuiteXmlFile=testng_E2E.xml'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'reports/**, screenshots/**', allowEmptyArchive: true
        }

        success {
            echo 'Tests executed successfully'
        }

        failure {
            echo 'Some tests failed'
        }

        cleanup {
            echo 'Pipeline finished'
        }
    }
}