#!/bin/bash

cd /Users/ZanderMac/Dropbox/NWU\ Pukke/\~\ Java\ Programming/Console\ Applications/2015/Secure\ Sign\ In\(Prototype\)/bin
javac -cp '.:activation.jar:javax.mail.jar' SecureSignIn.java
java -cp '.:activation.jar:javax.mail.jar' SecureSignIn
sleep 5
osascript -e 'tell application "Terminal" to quit' &
exit 0