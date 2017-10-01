//
//  ViewController.swift
//  Secure Sign In
//
//  Created by Zander Labuschagne on 2015/11/19.
//  Copyright © 2015 Cryon Software. All rights reserved.
//

//GUI
import Cocoa

//My own method to convert char to ascii value added to all char types
extension Character
{
    func toASCII() -> Int
    {
        let char = String(self);
        let ascii = char.unicodeScalars;
        
        return (Int)(ascii[ascii.startIndex].value);
    }
}

//My own method to convert ascii int to char added to all int types
extension Int
{
    func toChar() -> Character
    {
        return (Character)((UnicodeScalar)(self));
    }
}



class MainWindow: NSViewController, NSSharingServiceDelegate
{

    //enum is needed to throw exceptions
    enum InputError: ErrorType //InputError - Name of exceprtion
    {
        case passwordEmpty;
        case keyEmpty;
    }
    
    var cipherPassword = [Character]();

    override var representedObject: AnyObject?
    {
        didSet
        {
        }
    }
    @IBOutlet weak var pswPassword: NSSecureTextField!
    @IBOutlet weak var pswKey: NSSecureTextField!
    @IBOutlet weak var btnEncryptPassword: NSButton!
    
    
    
    func encrypt(plainPassword: [Character], key: [Character]) -> [Character]
    {
        var password = [Character]();//Declaring empty char array
        var cipherPassword = [Character]();
        var keyIndex = 0;
        var temp: Int;
        var specCharCount = 0;
        var pos = 0;
        var specChars = [Character]();
        
        //for(var iii = 0; iii < userPassword.count; iii++)
        for t in plainPassword
        {
            //let t = userPassword[iii];
            
            //var s = "\(t)";//Convert char t to string s
            
            //var keyCharAsString = "\(key[keyIndex])";
            //let keyCharAsString = String(key[keyIndex]).unicodeScalars;
            
            if (t.toASCII() >= 65 && t.toASCII() <= 90 )//Encrypting Uppercase Characters
            {
                temp = t.toASCII() - 65 + (key[keyIndex].toASCII() - 65);
                
                if (t.toASCII() < 0)
                {
                    temp += 26;
                }
                if (t.toASCII() <= 0)
                {
                    temp += 26;
                }
                
                password.append((65 + (temp % 26)).toChar());
                
                if (++keyIndex == key.count)
                {
                    keyIndex = 0;
                }
            }
                
            else if (t.toASCII() >= 97 && t.toASCII() <= 122)//Encrypting Lower Case Characters
            {
                temp = t.toASCII() - 97 + (key[keyIndex].toASCII() - 97);
                
                if (temp < 0)
                {
                    temp += 26;
                }
                if (temp < 0)
                {
                    temp += 26;
                }
                
                password.append((97 + (temp % 26)).toChar());

                if (++keyIndex == key.count)
                {
                    keyIndex = 0;
                }
            }
                
            else//Encrypting Special Characters
            {
                specChars.append((pos + 65).toChar());
                specChars.append(t);
                specCharCount++;
            }
            pos++;
        }
        cipherPassword.append(specCharCount == 0 ? 65.toChar() : (--specCharCount + 65).toChar());//Encrypting Amount of Special Characters in Password
        cipherPassword += specChars;
        cipherPassword += password;
    
        if(cipherPassword.count > 32)
        {
            let notification = NSAlert();//MessageBox
            notification.messageText = "Warning";
            notification.informativeText = "Password is greater than 32 characters";
            notification.runModal();//Show MessageBox
        }
        
        return cipherPassword;
    }
    
    override func shouldPerformSegueWithIdentifier(identifier: String, sender: AnyObject?) -> Bool
    {
        return true;//!pause;
    }
    
    override func prepareForSegue(segue: NSStoryboardSegue, sender: AnyObject?)
    {
        if(segue.identifier == "osdOutput")
        {
            let output = segue.destinationController as! OSDOutputWindow;
            output.password = cipherPassword;
        }
    }
   
    @IBAction func btnEncryptPassword_Click(sender: NSButton)
    {
        do
        {
            if(pswPassword.stringValue.isEmpty)
            {
                throw InputError.passwordEmpty;
            }
            if(pswKey.stringValue.isEmpty)
            {
                throw InputError.keyEmpty;
            }
            
            //let = constant
            //var = variable
            let plainPassword = [Character](pswPassword.stringValue.characters);
            let key = [Character](pswKey.stringValue.characters);//Convert String to char array
            
            cipherPassword = encrypt(plainPassword, key: key);
            
            performSegueWithIdentifier("osdOutput", sender: self);
        }
        catch InputError.passwordEmpty
        {
            //pause = true;
            pswPassword.becomeFirstResponder();
            let errorMessage = NSAlert();//MessageBox
            errorMessage.messageText = "Enter a Password";
            errorMessage.informativeText = "Please Enter a Password";
            errorMessage.runModal();//Show MessageBox
        }
        catch InputError.keyEmpty
        {
            //pause = true;
            pswKey.becomeFirstResponder();
            let errorMessage = NSAlert();//MessageBox
            errorMessage.messageText = "Enter a Key";
            errorMessage.informativeText = "Please Enter a Key";
            errorMessage.runModal();//Show MessageBox
        }
        catch
        {
            
        }

    }
  
    
    override func viewDidLoad()
    {
        super.viewDidLoad();
    }
    
}

