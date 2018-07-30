sudo rm -r /opt/SecureSignIn/
sudo mkdir /opt/SecureSignIn
sudo cp -p SecureSignIn-3.5b.jar SecureSignIn-3.5b.sh ssi.png /opt/SecureSignIn/
sudo cp -p SecureSignIn.desktop /usr/share/applications/
sudo chmod +x /usr/share/applications/SecureSignIn.desktop
sudo chmod +x /opt/SecureSignIn/SecureSignIn-3.5b.jar
sudo chmod +x /opt/SecureSignIn/SecureSignIn-3.5b.sh
