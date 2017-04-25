stage 'Checkout'
node { 
	checkout scm
	sh "pwd"
	sh "ls -l"
    sh "mvn clean"	 
    stash excludes: 'target/', includes: '**', name: 'source'
}
stage 'Test'
node {
    unstash 'source'  
    sh "mvn test"
    sh "mvn site"     
    stash includes: '**', name: 'report'
}
stage 'Reports'
node {
    unstash 'report'
    echo 'Test is over!'
    allure([includeProperties: false, jdk: '', properties: [], reportBuildPolicy: 'ALWAYS', results: [[path: 'target/allure-results']]])
}

stage 'Send mail'
node {
    emailext(body: '${DEFAULT_CONTENT}', mimeType: 'text/html',
         replyTo: '$DEFAULT_REPLYTO', subject: '${DEFAULT_SUBJECT}',
         to: emailextrecipients([[$class: 'CulpritsRecipientProvider'],
                                 [$class: 'RequesterRecipientProvider']]))
}