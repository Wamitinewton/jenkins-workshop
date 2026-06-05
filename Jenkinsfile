// Topic 1 — MULTIBRANCH
// Demonstrates: branch detection, per-branch stage gates, manual approval before production

pipeline {

    agent any

    environment {
        APP_NAME     = 'spring-demo'
        NOTIFY_EMAIL = 'wamitinewton@gmail.com'
        MAVEN_OPTS   = '-Xmx512m'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '5'))
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
        skipStagesAfterUnstable()
        disableConcurrentBuilds()
    }

    parameters {
        booleanParam(name: 'SKIP_TESTS',   defaultValue: false, description: 'Emergency bypass — skip all tests')
        booleanParam(name: 'FORCE_DEPLOY', defaultValue: false, description: 'Force Docker Build on any branch')
    }

    stages {

        stage('Branch Context') {
            steps {
                script {
                    echo "Branch : ${env.BRANCH_NAME} | Build : ${env.BUILD_NUMBER} | Commit : ${env.GIT_COMMIT?.take(8) ?: 'n/a'}"
                    env.IS_MASTER  = (env.BRANCH_NAME == 'master').toString()
                    env.IS_DEVELOP = (env.BRANCH_NAME == 'develop').toString()
                    env.IS_FEATURE = (env.BRANCH_NAME?.startsWith('feature/')).toString()
                }
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean compile -B -ntp'
            }
        }

        stage('Test') {
            when {
                not { expression { return params.SKIP_TESTS as boolean } }
            }
            steps {
                sh './mvnw test -B -ntp'
            }
            post {
                always {
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        stage('Package') {
            steps {
                sh './mvnw package -DskipTests -B -ntp'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, allowEmptyArchive: false
            }
        }

        stage('Docker Build') {
            when {
                anyOf {
                    branch 'master'
                    branch 'develop'
                    expression { return params.FORCE_DEPLOY as boolean }
                }
            }
            steps {
                script {
                    def tag = (env.BRANCH_NAME == 'master') ? 'latest' : env.BRANCH_NAME
                    echo "[DEMO] Docker image built and tagged as ${APP_NAME}:${tag} and ${APP_NAME}:build-${env.BUILD_NUMBER}"
                }
            }
        }

        stage('Deploy → Staging') {
            when { branch 'develop' }
            steps {
                echo "[DEMO] Would deploy ${APP_NAME}:develop to STAGING at http://localhost:8081"
            }
        }

        stage('Deploy → Production') {
            when { branch 'master' }
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    input message: "Deploy build #${env.BUILD_NUMBER} to PRODUCTION?", ok: 'Deploy Now'
                }
                echo "[DEMO] Would deploy ${APP_NAME}:latest to PRODUCTION at http://localhost:8080"
            }
        }
    }
}
