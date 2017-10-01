import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sun Jun 05 09:00:11 SAST 2016
 */



/**
 * @author Zander Labuschagne
 */
public class Display extends JDialog
{
    private char[] password;
    private int handler;
    private String s;
    //private Font hide = new Font("Noto Sans", Font.PLAIN, 12);
    //private Font show_Windows = new Font("Consolas", Font.PLAIN, 12);
    //private Font show_Linux = new Font("Noto Mono", Font.PLAIN, 12);
    //private Font show_MacOSX = new Font("PT Mono", Font.PLAIN, 12);
    //private final String OS = System.getProperty("os.name");

    public Display(Frame owner)
    {
        super(owner);
        initComponents();
    }

    public Display(Dialog owner)
    {
        super(owner);
        initComponents();
    }

    public Display(Frame owner, char[] password)
    {
        super(owner);
        initComponents();
        //pswPassword.setFont(hide);
        pswPassword.setFont(new Font("Courier", Font.PLAIN, 12));
         s = "";
        for(int i = 0; i < password.length; i++)
            s += '\u25cf';
        pswPassword.setText(s);
        this.password = password;
        handler = 0;
    }

    private void btnHideActionPerformed(ActionEvent e)
    {
        if(handler == 0)
        {
            /*if(OS.equals("Linux"))
                pswPassword.setFont(show_Linux);
            else if(OS.substring(0, 7).equals("Windows"))
                pswPassword.setFont(show_Windows);
            else if(OS.equals("Mac OS X"))
                pswPassword.setFont(show_MacOSX);*/
            pswPassword.setText(new String(password));
            btnHide.setText("Hide Password");
            handler = 1;
        }
        else
        {
            //pswPassword.setFont(hide);
            pswPassword.setText(s);
            btnHide.setText("Reveal Password");
            handler = 0;
        }
    }

    private void btnOKActionPerformed(ActionEvent e)
    {
        dispose();
    }

    private void btnCopyActionPerformed(ActionEvent e)
    {
        StringSelection stringSelection = new StringSelection(new String(password));
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);

        dispose();
    }
    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Zander Labuschagne
        pswPassword = new JFormattedTextField();
        btnHide = new JButton();
        btnCopy = new JButton();
        btnOK = new JButton();

        //======== this ========
        setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setType(Window.Type.POPUP);
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
            "5dlu, 2*($lcgap, 85dlu), $lcgap, 35dlu, $lcgap, 5dlu",
            "6dlu, $lgap, default, $lgap, 3dlu, $lgap, default, $lgap, 3dlu"));

        //---- pswPassword ----
        pswPassword.setEditable(false);
        pswPassword.setFont(new Font("Noto Mono", Font.PLAIN, 12));
        contentPane.add(pswPassword, CC.xywh(3, 3, 5, 1));

        //---- btnHide ----
        btnHide.setText("Reveal Password");
        btnHide.addActionListener(e -> btnHideActionPerformed(e));
        contentPane.add(btnHide, CC.xywh(2, 7, 2, 1));

        //---- btnCopy ----
        btnCopy.setText("Copy Password");
        btnCopy.addActionListener(e -> btnCopyActionPerformed(e));
        contentPane.add(btnCopy, CC.xywh(4, 7, 3, 1));

        //---- btnOK ----
        btnOK.setText("OK");
        btnOK.addActionListener(e -> btnOKActionPerformed(e));
        contentPane.add(btnOK, CC.xywh(7, 7, 2, 1));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Zander Labuschagne
    private JFormattedTextField pswPassword;
    private JButton btnHide;
    private JButton btnCopy;
    private JButton btnOK;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
