pipeline {
    agent any

    tools {
        maven 'Maven-3.9.11'

    }
//Build stages
    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                git branch: 'main',
                    url: 'https://github.com/biswalsatabdi/Selenium-Test-Automation-Framework.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building application...'
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                bat 'mvn test'
            }
        }

        stage('Reports') {
            steps {
                echo 'Publishing Extent Report...'

                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'src/test/resources/ExtentReport',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Spark Report'
                ])
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution completed'

            junit 'target/surefire-reports/*.xml'

            archiveArtifacts artifacts: 'src/test/resources/ExtentReport/*.html',
                             fingerprint: true
        }

        success {
            echo 'BUILD SUCCESS 🎉'

            emailext(
                to: 'satabdibiswal4648@gmail.com',
                subject: "BUILD SUCCESS : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                body: """
                <html>
                <body>
                    <h3>Build Successful ✅</h3>
                    <p>Job: ${env.JOB_NAME}</p>
                    <p>Build Number: ${env.BUILD_NUMBER}</p>
                    <p><a href="${env.BUILD_URL}">View Build</a></p>
                </body>
                </html>
                """
            )
        }

        failure {
            echo 'BUILD FAILED ❌'

            emailext(
                to: 'satabdibiswal4648@gmail.com',
                subject: "BUILD FAILED : ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                body: """
                <html>
                <body>
                    <h3>Build Failed ❌</h3>
                    <p>Job: ${env.JOB_NAME}</p>
                    <p>Build Number: ${env.BUILD_NUMBER}</p>
                    <p><a href="${env.BUILD_URL}">Check Logs</a></p>
                </body>
                </html>
                """
            )
        }
    }
}
