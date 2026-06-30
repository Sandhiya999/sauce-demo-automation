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
    options {
    timestamps()
    disableConcurrentBuilds()
    buildDiscarder(logRotator(numToKeepStr:'10'))
    }

    parameters{
        string(name: 'SUITE_FILE' ,defaultValue: 'testng.xml', description: 'TestNG suite file to run')
    }

    stages {
        stage('Clean') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Test') {
            steps {
                bat "mvn test -DsuiteXmlFile=$(params.SUITE_FILE)"
            }
        }
    }

    post {
        always {
            junit allowEmptyResult: true, testResults:'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'reports/**, screenshots/**', allowEmptyArchive: true
        }

        success {
            echo 'Tests executed successfully'
        }

        failure {
            echo 'Some tests failed'
        }

        cleanup {
            cleanWs()
            echo 'Pipeline finished'
        }
    }
}