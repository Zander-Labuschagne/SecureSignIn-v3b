# Secure Sign In v3b *Legacy*
This is a JavaFX application I have created in an attempt to improve my online account security. The _b_ in the version code states that this is the GUI(Graphical User Interface) version where _a_ would be the CLI(Command Line Interface). Find the new release(Qt GUI apllication) at https://gitlab.com/Zander-Labuschagne/SecureSignIn-v4b or https://github.com/Zander-Labuschagne/SecureSignIn-v4b.

Benifits:
  - Remember one password for all sites, but all sites have different passwords.
  - Don't know the actual password which is entered in the password box on the website.
  - Provides a very strong, long and complex password.
  - No passwords are stored in file or database.
  - Easy to use.
  
This(_v3b_) is my first JavaFX application I have created and my first Linux deployable application. Feel free to criticize or comment.
There are Android, iPhone, iPad and macOS applications available as well, however they are not always up to date and I have left some of them discontinued/incomplete. I work on these projects in my free time only so don't expect regular updates from me.

macOS(Swift) version: TBA

Android version: _Discontinued_

iOS(iPhone) version: TBA

iOS(iPad) version: TBA
  
The logo and name on the application comes from an iWorks template, it is not a registered company name or logo, I just added it to make it look impressive.

E-Mail: <zander.labuschagne@protonmail.ch>

Copyright (C) 2018 Zander Labuschagne. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3 as published by the Free Software Foundation.

How to install:
  1. Oracle Java Runtime Environment 10 is required to run the application, OpenJDK does not work well with JavaFX. JRE and JDK versions older than 10 is not expected to work, versions 11 and above does not work, use Secure Sign In v4b version instead.
  2. Either run the SecureSignIn-3.5b.jar file inside the bin folder on any operating system(double click the file or execute ``java -jar SecureSignIn-3.5b.jar`` in the terminal) or run the install.sh file after extracting the zip file to install the application on Linux systems with the following command: ``sudo sh install.sh``.
  3. On some Linux systems it's necessary to run ``sudo chmod +x install.sh`` before installation.
 
 (Feel free to create Windows and macOS versions of the install)


How to use application:
  1. Enter a password you will remember in the password box, preferably a strong and complicated password because this will influence the complexity of the resulting password.
  2. Enter a key, such as google, facebook or whatever.
  3. Click on Encrypt Password and the rest is self explanatory.
  4. Some website have limitations on the length of the passwords, hence the compact password toggle button.
