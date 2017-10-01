import sun.awt.resources.awt;

//GUI
import javax.swing.*;

//Display
import java.awt.event.*;
import java.awt.Container;

//E-Mail
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;
import java.util.Properties;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author Zander Labuschagne 23585137
 * This Class is the main GUI Window / Class for Zander's attempt to improve online account security
 */
public class MainWindow
{
    //GUI Instance Variables
    private JPanel panel1;
    private JPasswordField pswPassword;
    private JPasswordField pswKey;
    private JCheckBox cckOSD;
    private JCheckBox cckSMS;
    private JCheckBox cckEMail;
    private JButton btnEncrypt;

    //Instance Variables
    private char[] password;
    private char[] key;
    private char[] finalPassword;
    private String email;
    private String cellular;

    public  Container contentPane;

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            JFrame frame = new JFrame("MainWindow");
            frame.setContentPane(new MainWindow().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);//Center the screen
            frame.setVisible(true);
            frame.setTitle("Secure Sign In");
        }
        catch (Exception ex)
        {
            ;
        }
    }

    /**
     * Default Constructor
     */
    public MainWindow()
    {
        password = null;
        key = null;
        finalPassword = null;
        email = null;
        cellular = null;
        panel1.setFocusable(true);
        pswPassword.setFocusable(true);
        pswPassword.requestFocus();

        btnEncrypt.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    password = pswPassword.getPassword();
                    if (new String(password).equals(""))
                    {
                        pswPassword.requestFocus();
                        throw new Exception("Please Enter a Password");
                    }
                    key = pswKey.getPassword();
                    if (new String(key).equals(""))
                    {
                        pswKey.requestFocus();
                        throw new Exception("Please Enter a Key");
                    }

                    finalPassword = encrypt(password, key);
                    if(finalPassword == null)
                        throw new EncryptionException("Error Occured During Encryption Process");

/////////////////////////////////////////////////////////////////
                    OSD osd = new OSD(finalPassword);
                    osd.pack();
                    osd.setLocationRelativeTo(null);
                    osd.setAlwaysOnTop(true);
                    osd.setVisible(true);

                    if (cckOSD.isSelected())
                    {
                        //OSD osd = new OSD(finalPassword);
                        osd.pack();
                        osd.setLocationRelativeTo(null);
                        osd.setAlwaysOnTop(true);
                        osd.setVisible(true);
                    }
                    if (cckEMail.isSelected())
                    {
                        InetAddress gmail = InetAddress.getByName("slashdot.org");
                        if(!gmail.isReachable(2000))
                            throw new InternetConnectionException("Failed to Send E-Mail: Check Internet Connection");
                        JOptionPane.showMessageDialog(null, "PLEASE!! use a secure and encrypted E-Mail service for this!\n(NOT GMAIL or YAHOO for example)", "Caution", JOptionPane.WARNING_MESSAGE);
                        String email = JOptionPane.showInputDialog(null, "Please Enter Your Secure E-Mail Address:", "Enter E-Mail", JOptionPane.PLAIN_MESSAGE);
                        if(email != null)
                        {
                            String emailErrorStatus = sendEMail(email, "CLASSIFIED", (new String(finalPassword) + "\r\n\r\n"));
                            if(emailErrorStatus == null)
                                JOptionPane.showMessageDialog(null, "E-Mail Sent", "E-Mail Sent", JOptionPane.PLAIN_MESSAGE);
                            else
                                throw new EMailException(emailErrorStatus);
                        }
                    }
                    if (cckSMS.isSelected())
                    {
                        InetAddress gmail = InetAddress.getByName("slashdot.org");
                        if(!gmail.isReachable(2000))
                            throw new InternetConnectionException("Failed to Communicate with Internet");
                        String cellular = JOptionPane.showInputDialog(null, "Please Enter Your Secure Cellular Number:", "Enter Cullular Number", JOptionPane.PLAIN_MESSAGE);
                        if(cellular != null && cellular.length() == 10)
                        {
                            cellular += "@voda.co.za";
                            String smsErrorStatus = sendEMail(cellular, "CLASSIFIED", (new String(finalPassword) + "\t\r\n\r\n"));
                            if(smsErrorStatus == null)
                                JOptionPane.showMessageDialog(null, "SMS Sent", "SMS Sent", JOptionPane.PLAIN_MESSAGE);
                            else
                                throw new CellularException(smsErrorStatus);
                        }
                        else if(cellular.length() != 10)
                            throw new CellularException("Invalid Cell Number");
                    }
                    if (!cckEMail.isSelected() && !cckOSD.isSelected() && !cckSMS.isSelected())
                    {
                        cckOSD.requestFocus();
                        throw new Exception("Please Choose an Output Method");
                    }
                }
                catch (EncryptionException eex)
                {
                    JOptionPane.showMessageDialog(null, eex.getMessage(), "Password Encryption Error", JOptionPane.ERROR_MESSAGE);
                }
                catch(CellularException cex)
                {
                    JOptionPane.showMessageDialog(null, cex.getMessage(), "Failed to Send SMS", JOptionPane.ERROR_MESSAGE);
                }
                catch(UnknownHostException uhex)
                {
                    JOptionPane.showMessageDialog(new JFrame(), "E-Mail/SMS Failed to Send:\nCheck Internet Connection", "Unable to Connect to Internet", JOptionPane.ERROR_MESSAGE);
                }
                catch(InternetConnectionException icex)
                {
                    JOptionPane.showMessageDialog(null, icex.getMessage(), "Check Internet Connection", JOptionPane.ERROR_MESSAGE);
                }
                catch (EMailException emex)
                {
                    JOptionPane.showMessageDialog(null, emex.getMessage(), "Failed to Send Email", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnEncrypt.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
            }
        });
        btnEncrypt.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(e.getKeyCode() == 0)
                btnEncrypt.doClick();
                super.keyTyped(e);
            }
        });
        panel1.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(e.getKeyCode() == 0)
                btnEncrypt.doClick();
                super.keyTyped(e);
            }
        });
        pswPassword.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(e.getKeyCode() == 0)
                btnEncrypt.doClick();
                super.keyTyped(e);
            }
        });
        pswKey.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(e.getKeyCode() == 0)
                btnEncrypt.doClick();
                super.keyTyped(e);
            }
        });
        cckOSD.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(e.getKeyCode() == 0)
                btnEncrypt.doClick();
                super.keyTyped(e);
            }
        });
        cckEMail.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(e.getKeyCode() == 0)
                btnEncrypt.doClick();
                super.keyTyped(e);
            }
        });
        cckSMS.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(e.getKeyCode() == 0)
                btnEncrypt.doClick();
                super.keyTyped(e);
            }
        });
    }

    /**
     * Method to encrypt the password
     * Based on Viginere's Cipher Algorithm, modified by Zander
     * @param userPassword the password to be encrypted
     * @param key the key used to encrypt the password
     * @return the encrypted password
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
            finalPassword[i++] = (char)(specCharCount == 0 ? 65 : (--specCharCount + 65));//Encrypting Amount of Special Characters in Password
            for(char t = specChars[0]; t != 0; i++, t = specChars[i - 1])//Encrypting Special Characters & Positions of Special Characters
                finalPassword[i] = t;
            ii = i;
            for(char t = systemPassword[0]; t != 0; i++, t = systemPassword[i - ii])//Encrypting Password
                finalPassword[i] = t;

            if(i > 32)
                JOptionPane.showMessageDialog(null, "Password is greater than 32 characters", "Warning", JOptionPane.WARNING_MESSAGE);
                
           int length = 0;
           for(int x = 0; finalPassword[x] != '\0'; x++)
                     length++;
            char[] cipherPassword = new char[length];
            for(int xi = 0; xi < length; xi++)
                cipherPassword[xi] = finalPassword[xi];

            return cipherPassword;
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


    /**
     * Don't Know
     */
    private void createUIComponents()
    {
        // TODO: place custom component creation code here
    }


}
