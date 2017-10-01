import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sun Jun 05 06:47:23 SAST 2016
 */



/**
 * @author Zander Labuschagne
 */
public class SecureSignIn extends JFrame
{
    //Instance Variables
    private char[] plainPassword;
    private char[] key;
    private char[] cipherPassword;
    //private final String OS = System.getProperty("os.name");

    /**
     * Default Constructor
     */
    public SecureSignIn()
    {
        initComponents();
        plainPassword = null;
        key = null;
        cipherPassword = null;
    }

    /**
     * Overloaded Constructor
     * @param OS operating system
     */
    public SecureSignIn(String OS)
    {
        this();
        if(OS.equals("Linux"))
            label1.setIcon(new ImageIcon(this.getClass().getResource("CryogenSoftware/cryogen_blue_landscape_dark_theme.png")));
        else
            label1.setIcon(new ImageIcon(this.getClass().getResource("CryogenSoftware/cryogen_blue_landscape_light_theme.png")));
    }

    private void btnEncryptActionPerformed(ActionEvent e)
    {
        try
        {
            plainPassword = pswPassword.getPassword();
            if (new String(plainPassword).equals(""))
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

            int limit = 32;
            if(cmbLimit.getSelectedItem().equals("8"))
                limit = 8;
            else if(cmbLimit.getSelectedItem().equals("10"))
                limit = 10;
            else if(cmbLimit.getSelectedItem().equals("12"))
                limit = 12;
            else if(cmbLimit.getSelectedItem().equals("20"))
                limit = 20;

            cipherPassword = encrypt(plainPassword, key, limit);
            if(cipherPassword == null)
                throw new Exception("Error Occurred During Encryption");

            Display osd = new Display(this, cipherPassword);
            osd.pack();
            osd.setLocationRelativeTo(null);
            osd.setAlwaysOnTop(true);
            osd.setVisible(true);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to encrypt the password
     * Based on Viginere's Cipher Algorithm, modified by Zander
     *
     * @param userPassword the password to be encrypted
     * @param key          the key used to encrypt the password
     * @return the encrypted password
     */
    public static char[] encrypt(char[] userPassword, char[] key, int limit)
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
            char[] specChars = new char[userPassword.length + 1];

            for (char t : userPassword)
            {
                if (t >= 65 && t <= 90)//Encrypting Uppercase Characters
                {
                    temp = t - 65 + (key[keyIndex] - 65);
                    if (temp < 0)
                        temp += 26;
                    if (temp <= 0)
                        temp += 26;

                    systemPassword[i++] = (char) (65 + (temp % 26));
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

                    systemPassword[i++] = (char) (97 + (temp % 26));
                    if (++keyIndex == key.length)
                        keyIndex = 0;
                }
                else//Encrypting Special Characters
                {
                    specChars[ii++] = (char) (pos + 65);
                    specChars[ii++] = t;
                    specCharCount++;
                }
                pos++;
            }
            i = 0;
            finalPassword[i++] = (char) (specCharCount == 0 ? 65 : (--specCharCount + 65));//Encrypting Amount of Special Characters in Password
            for (char t = specChars[0]; t != 0; i++, t = specChars[i - 1])//Encrypting Special Characters & Positions of Special Characters
                finalPassword[i] = t;
            ii = i;
            for (char t = systemPassword[0]; t != 0; i++, t = systemPassword[i - ii])//Encrypting Password
                finalPassword[i] = t;

            int ext = -1;
            if(i > 32)
            {
                String[] options = new String[2];
                options[1] = "Yes";
                options[0] = "No";
                ext = JOptionPane.showOptionDialog(null, "Password is greater than 32 characters.\nWould you like to shorten the password to the 32 limit?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon(), options, 0);
            }

            int length = 0;
            for(int x = 0; finalPassword[x] != '\0'; x++)
                length++;
            char[] cipherPassword = new char[length];
            for(int xi = 0; xi < cipherPassword.length && xi < length; xi++)
                cipherPassword[xi] = finalPassword[xi];

            //Shuffle Password
            LinkedList<Character>evens = new LinkedList<>();
            LinkedList<Character>odds = new LinkedList<>();
            for(int iii = 0; iii < cipherPassword.length; iii++)
                if((int)cipherPassword[iii] % 2 == 0)
                    evens.addLast(cipherPassword[iii]);
                else
                    odds.addFirst(cipherPassword[iii]);
            int iv = 0;
            while(!evens.isEmpty() || !odds.isEmpty())
            {
                if (!odds.isEmpty())
                {
                    cipherPassword[iv++] = odds.getFirst();
                    odds.removeFirst();
                }
                if(!evens.isEmpty())
                {
                    cipherPassword[iv++] = evens.getFirst();
                    evens.removeFirst();
                }
            }

            //encrypt special chars further
            for(int v = 0; v < cipherPassword.length; v++)
                if((int)cipherPassword[v] <= 47)
                    cipherPassword[v] += 10;
                else if((int)cipherPassword[v] > 47 && (int)cipherPassword[v] < 64)
                    cipherPassword[v] -= 5;
                else if((int)cipherPassword[v] > 90 && (int)cipherPassword[v] <= 96)
                    if(cipherPassword.length % 2 == 0)
                        cipherPassword[v] += 2;
                    else
                        cipherPassword[v] -= 2;

            //Replacing unloved characters
            for(int vi = 0; vi < cipherPassword.length; vi++)
                if((int)cipherPassword[vi] == 34)
                    cipherPassword[vi] = 123;
                else if((int)cipherPassword[vi] == 38)
                    cipherPassword[vi] = 124;
                else if((int)cipherPassword[vi] == 60)
                    cipherPassword[vi] = 125;
                else if((int)cipherPassword[vi] == 62)
                    cipherPassword[vi] = 126;

            //Limitations
            if(ext == 1 || limit < 32)
            {
                char[] cipherPasswordLimited = new char[limit < cipherPassword.length ? limit : cipherPassword.length];
                for (int vii = 0; vii < cipherPassword.length && vii < limit; vii++)
                    cipherPasswordLimited[vii] = cipherPassword[vii];
                return cipherPasswordLimited;
            }

            return cipherPassword;
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.toString(), "Failed to Encrypt Password", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Zander Labuschagne
        DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
        vSpacer2 = new JPanel(null);
        label1 = new JLabel();
        hSpacer1 = new JPanel(null);
        lblPassword = compFactory.createLabel("Password:");
        pswPassword = new JPasswordField();
        hSpacer2 = new JPanel(null);
        lblKey = new JLabel();
        pswKey = new JPasswordField();
        vSpacer3 = new JPanel(null);
        lblPasswoordLimit = new JLabel();
        cmbLimit = new JComboBox<>();
        btnEncrypt = new JButton();
        vSpacer1 = new JPanel(null);

        //======== this ========
        setTitle("Cryogen Software: Secure Sign In 2.1");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(new Color(0, 51, 102));
        setForeground(Color.black);
        setType(Window.Type.UTILITY);
        setIconImage(new ImageIcon(getClass().getResource("/CryogenSoftware/cryogen_blue_icon_64.png")).getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
            "10dlu, $lcgap, default, $lcgap, 2dlu, 20dlu, $lcgap, 105dlu, $lcgap, 35dlu, $lcgap, 10dlu",
            "3*(15dlu, $lgap), 2*(default, $lgap), 15dlu, $lgap, default, $lgap, 10dlu"));

        //---- vSpacer2 ----
        vSpacer2.setVisible(false);
        contentPane.add(vSpacer2, CC.xywh(6, 1, 1, 5));

        //---- label1 ----
        label1.setIcon(null);
        contentPane.add(label1, CC.xywh(6, 1, 3, 5, CC.CENTER, CC.DEFAULT));
        contentPane.add(hSpacer1, CC.xy(1, 7));

        //---- lblPassword ----
        lblPassword.setLabelFor(pswPassword);
        lblPassword.setVerticalAlignment(SwingConstants.BOTTOM);
        contentPane.add(lblPassword, CC.xy(3, 7));

        //---- pswPassword ----
        pswPassword.setEchoChar('\u25cf');
        contentPane.add(pswPassword, CC.xywh(6, 7, 5, 1));
        contentPane.add(hSpacer2, CC.xy(12, 7));

        //---- lblKey ----
        lblKey.setText("Key:");
        lblKey.setLabelFor(pswKey);
        lblKey.setVerticalAlignment(SwingConstants.BOTTOM);
        contentPane.add(lblKey, CC.xy(3, 9));

        //---- pswKey ----
        pswKey.setEchoChar('\u25cf');
        contentPane.add(pswKey, CC.xywh(6, 9, 5, 1));
        contentPane.add(vSpacer3, CC.xy(6, 11));

        //---- lblPasswoordLimit ----
        lblPasswoordLimit.setText("Password Length Limit:  ");
        lblPasswoordLimit.setHorizontalAlignment(SwingConstants.TRAILING);
        lblPasswoordLimit.setVerticalAlignment(SwingConstants.BOTTOM);
        contentPane.add(lblPasswoordLimit, CC.xy(8, 11));

        //---- cmbLimit ----
        cmbLimit.setModel(new DefaultComboBoxModel<>(new String[] {
            "8",
            "10",
            "12",
            "20",
            "32"
        }));
        cmbLimit.setSelectedIndex(4);
        contentPane.add(cmbLimit, CC.xywh(9, 11, 2, 1));

        //---- btnEncrypt ----
        btnEncrypt.setText("Encrypt Password");
        btnEncrypt.setIcon(null);
        btnEncrypt.addActionListener(e -> btnEncryptActionPerformed(e));
        contentPane.add(btnEncrypt, CC.xywh(2, 13, 9, 1));
        contentPane.add(vSpacer1, CC.xy(8, 15));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Zander Labuschagne
    private JPanel vSpacer2;
    private JLabel label1;
    private JPanel hSpacer1;
    private JLabel lblPassword;
    private JPasswordField pswPassword;
    private JPanel hSpacer2;
    private JLabel lblKey;
    private JPasswordField pswKey;
    private JPanel vSpacer3;
    private JLabel lblPasswoordLimit;
    private JComboBox<String> cmbLimit;
    private JButton btnEncrypt;
    private JPanel vSpacer1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                String OS = System.getProperty("os.name");
                try
                {
                    if(OS.equalsIgnoreCase("Linux"))
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                    else if(OS.equalsIgnoreCase("Windows 7") || OS.equalsIgnoreCase("Windows 8") || OS.equalsIgnoreCase("Windows 8.1") || OS.equalsIgnoreCase("Windows 10"))
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    else if(OS.equalsIgnoreCase("Windows XP") || OS.equalsIgnoreCase("Windows ME") || OS.equalsIgnoreCase("Windows 2000") || OS.equalsIgnoreCase("Windows 98") || OS.equalsIgnoreCase("Windows 95"))
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
                    //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    //UIManager.setLookAndFeel("com.bulenkov.darcula.DarculaLaf");
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (InstantiationException e)
                {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                catch (UnsupportedLookAndFeelException e)
                {
                    e.printStackTrace();
                }
                //System.setProperty("sun.java2d.opengl", "True");
                SecureSignIn ssi = new SecureSignIn(OS);
                ssi.pack();
                ssi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ssi.setVisible(true);
            }
        });
    }
}
