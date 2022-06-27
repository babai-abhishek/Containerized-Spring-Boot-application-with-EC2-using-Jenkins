pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                echo 'checking out the application'
                deleteDir()
                checkout scm
            }
        }
        stage('Build stage') {
            steps {
                withMaven(maven : 'maven_3.6.1'){
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        stage('Deploy stage') {
            steps {
                script{
    //                    def cm = 'copy ".\\Customer-service\\target\\*.war" "C:\\tomcat\\apache-tomcat-9.0.64\\webapps"'
                       sh "pwd"
                       def cm = 'sshpass -p \'password@123\' scp -o StrictHostKeyChecking=no ./Customer-service/target/*.war  Administrator@34.229.204.86:C:\/apache-tomcat-9.0.64\/webapps'
                       echo "${cm}"
                       sh "${cm}"
                  }
            }
        }
    }
}
