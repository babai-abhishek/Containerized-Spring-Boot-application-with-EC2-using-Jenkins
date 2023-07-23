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
                    sh 'mvn clean install -DskipTests -pl "!Sales, !Inventory"'
                }
            }
        }
        stage('Deploy stage') {
            steps {
                script{
                       sh "pwd"
                       def cm = 'sshpass -p \'@)2eJ9C?X(zJw;czt9GF6YT4G5Bx9uH5\' scp -o StrictHostKeyChecking=no ./Customer-service/target/*.war  Administrator@34.227.229.158:C:/apache-tomcat-9.0.64/webapps'
                       echo "${cm}"
                       sh "${cm}"
                  }
            }
        }
    }
}
