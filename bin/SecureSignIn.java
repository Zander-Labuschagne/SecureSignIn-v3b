
/*
* @author Zander Labuschagne 23585137
* This Class is Zander's attempt to improve online accounts security
*/

import javax.swing.JOptionPane;
import java.util.Scanner;
import java.io.Console;

import java.util.Arrays;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;
import javax.activation.*;
import java.util.Properties;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.*;

public class SecureSignIn
{	
	/**
	* Method to encrypt a password
	* @param userPassword Password to be encrypted and returned as the encrypted password
	* @param key The key that will be used to encrypt the password, each account's key is different
	*/
	public static char[] encrypt(char[] userPassword, char[] key)
	{
		try
		{
			char[] systemPassword = new char[userPassword.length + 1];
			char[] finalPassword = new char[userPassword.length * 2 + 1];
			int keyIndex = 0;
			int i = 0;
			int ii = 0;
			int temp;
			int specCharCount = 0;
			int pos = 0;
			char[] specChars = new char[finalPassword.length];

			for(char t : userPassword)
			{
				if (t >= 65 && t <= 90)//Encrypting Uppercase Characters
				{
					temp = t - 65 + (key[keyIndex] - 65);
					if (temp < 0)
						temp += 26;
					if (temp <= 0)
						temp += 26;

					systemPassword[i++] = (char)(65 + (temp % 26));
					if (++keyIndex == key.length)
						keyIndex = 0;
				}

				else if (t >= 97 && t <= 122)//Encrypting Lower Case Characters
				{
					temp = t - 97 + (key[keyIndex] - 97);
					if (temp < 0)
						temp += 26;
					if (temp < 0)
						temp += 26;

					systemPassword[i++] = (char)(97 + (temp % 26));
					if (++keyIndex == key.length)
						keyIndex = 0;
				}

				else//Encrypting Special Characters
				{
					specChars[ii++] = (char)(pos + 65);
					specChars[ii++] = t;
					specCharCount++;
				}
				pos++;
			}
			i = 0;
			finalPassword[i] = (char)(specCharCount == 0 ? 65 : (--specCharCount + 65));//Encrypting Amount of Special Characters in Password
			for(char t = specChars[0]; t != 0; i++, t = specChars[i])//Encrypting Special Characters & Positions of Special Characters
				finalPassword[i] = t;
			ii = i;
			for(char t = systemPassword[0]; t != 0; i++, t = systemPassword[i - ii])//Encrypting Password
				finalPassword[i] = t;

			if(i > 32)
				JOptionPane.showMessageDialog(null, "Password is greater than 32 characters", "Warning", JOptionPane.WARNING_MESSAGE);
			
			return finalPassword;
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.toString(), "Failed to Encrypt Password", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	public static String sendEMail(String address, String subject, String text)
	{
		try
		{
			Properties properties = System.getProperties();
			properties.setProperty("mail.smtp.host", "smtp.gmail.com"); 
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.store.protocol", "pop3");
			properties.put("mail.transport.protocol", "smtp");
			properties.put("mail.smtp.port", "587");
			
			Session session = Session.getDefaultInstance(properties, new Authenticator()
			{
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication("codebusters.guesthouse@gmail.com", "CODEBUSTERS");
				}
			});
			
			MimeMessage message = new MimeMessage(session);
			//message.setFrom(new InternetAddress("codebusters.guesthouse@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
			message.setSubject("subject");
			message.setText(text);
			
			Transport.send(message);
			
			return null;
		}
		catch(MessagingException mex)
		{
			return mex.toString();
		}
	}


	private static void mainOutput(char[] finalPassword, String[] args)
	{
		try
		{
			if(finalPassword != null)
			{
				Object[] methods = {"Display On Screen", "E-Mail", "SMS"};
				int method = JOptionPane.showOptionDialog(null, "How do you want to receive your password?", "Method of Output", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, methods, methods[0]);
				if(method == 0)
					System.out.println("Encrypted Password: " + (new String(finalPassword) + "\r\n\r\n"));
				else if(method == 1)
				{
					InetAddress gmail = InetAddress.getByName("slashdot.org");
					if(gmail.isReachable(2000))
					{
						JOptionPane.showMessageDialog(null, "PLEASE!! use a secure and encrypted E-Mail service for this!\n(NOT GMAIL or YAHOO for example)", "Caution", JOptionPane.WARNING_MESSAGE);
						String email = JOptionPane.showInputDialog(null, "Please Enter Your Secure E-Mail Address:", "Enter E-Mail", JOptionPane.QUESTION_MESSAGE);
						if(email != null)
						{
							
							String emailErrorStatus = sendEMail(email, "CLASSIFIED", (new String(finalPassword) + "\r\n\r\n"));
							if(emailErrorStatus == null)
								JOptionPane.showMessageDialog(null, "E-Mail Sent", "E-Mail Sent", JOptionPane.INFORMATION_MESSAGE);
							else
								throw new EMailException(emailErrorStatus);
						}
					}
					else
						throw new InternetConnectionException("Failed to Communicate with Internet");
				}
				else if(method == 2)
				{
					InetAddress gmail = InetAddress.getByName("slashdot.org");
					if(gmail.isReachable(2000))
					{
						String cellular = JOptionPane.showInputDialog(null, "Please Enter Your Secure Cellular Number:", "Enter Cullular Number", JOptionPane.QUESTION_MESSAGE);
						if(cellular != null && cellular.length() == 10)
						{
							cellular += "@voda.co.za";
							String smsErrorStatus = sendEMail(cellular, "CLASSIFIED", (new String(finalPassword) + "\t\r\n\r\n"));
							if(smsErrorStatus == null)
								JOptionPane.showMessageDialog(null, "SMS Sent", "SMS Sent", JOptionPane.INFORMATION_MESSAGE);
							else
								throw new CellularException(smsErrorStatus);
						}
						else if(cellular.length() != 10)
							throw new CellularException("Invalid Cell Number");
					}
					else
						throw new InternetConnectionException("Failed to Communicate with Internet");
				}
			}
			else
				throw new EncryptionException("Error Occured in Encryption Process");
		}
		catch(UnknownHostException uhex)
		{
			JOptionPane.showMessageDialog(null, "Check Internet Connection", "Unable to Connect to Internet", JOptionPane.ERROR_MESSAGE);
			mainOutput(finalPassword, args);
		}
		catch(EncryptionException eex)
		{
			JOptionPane.showMessageDialog(null, eex.getMessage(), "Failed to Send SMS", JOptionPane.ERROR_MESSAGE);
			main(args);
		}
		catch(InternetConnectionException icex)
		{
			JOptionPane.showMessageDialog(null, icex.getMessage(), "Check Internet Connection", JOptionPane.ERROR_MESSAGE);
			mainOutput(finalPassword, args);
		}
		catch(CellularException cex)
		{
			JOptionPane.showMessageDialog(null, cex.getMessage(), "Failed to Send SMS", JOptionPane.ERROR_MESSAGE);
			mainOutput(finalPassword, args);
		}
		catch(EMailException emex)
		{
			JOptionPane.showMessageDialog(null, emex.getMessage(), "Failed to Send Email", JOptionPane.ERROR_MESSAGE);
			mainOutput(finalPassword, args);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Password Encryption Failed", JOptionPane.ERROR_MESSAGE);
			main(args);
		}

	}
	public static void main(String[] args) 
	{
		Scanner input = new Scanner(System.in);
		Console console = System.console();
				
		//Zander's Password Encryption attempt to improve online account security
		System.out.print("Enter the Password to be Encrypted: ");
		char[] password = console.readPassword();
		System.out.print("Enter the Encryption Key: ");
		char[] key = console.readPassword();
		char[] finalPassword = encrypt(password, key);
		
		mainOutput(finalPassword, args);
	}
}