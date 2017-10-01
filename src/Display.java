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
            pswPassword.setText(new String(password));
            btnHide.setText("Hide Password");
            handler = 1;
        }
        else
        {
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
            "default, $lcgap, 75dlu, $lcgap, 70dlu, $lcgap, 25dlu, $lcgap, default",
            "6dlu, $lgap, default, $lgap, 3dlu, $lgap, default, $lgap, 3dlu"));

        //---- pswPassword ----
        pswPassword.setEditable(false);
        contentPane.add(pswPassword, CC.xywh(3, 3, 5, 1));

        //---- btnHide ----
        btnHide.setText("Reveal Password");
        btnHide.addActionListener(e -> btnHideActionPerformed(e));
        contentPane.add(btnHide, CC.xy(3, 7));

        //---- btnCopy ----
        btnCopy.setText("Copy Password");
        btnCopy.addActionListener(e -> btnCopyActionPerformed(e));
        contentPane.add(btnCopy, CC.xywh(4, 7, 3, 1));

        //---- btnOK ----
        btnOK.setText("OK");
        btnOK.addActionListener(e -> btnOKActionPerformed(e));
        contentPane.add(btnOK, CC.xy(7, 7));
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
