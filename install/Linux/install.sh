sudo rm -r /opt/SecureSignIn/
sudo mkdir /opt/SecureSignIn
sudo cp -p SecureSignIn-3.3.jar SecureSignIn-3.3.sh ssi.png /opt/SecureSignIn/
sudo cp -p SecureSignIn.desktop /usr/share/applications/
sudo chmod +x /usr/share/applications/SecureSignIn.desktop
sudo chmod +x /opt/SecureSignIn/SecureSignIn-3.3.jar
sudo chmod +x /opt/SecureSignIn/SecureSignIn-3.3.sh
