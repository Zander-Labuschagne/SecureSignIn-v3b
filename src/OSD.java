import javax.swing.*;
import java.awt.event.*;

import java.awt.datatransfer.*;
import java.awt.Toolkit;

public class OSD extends JDialog
{
    //GUI Instance Variables
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton btnHide;
    private JFormattedTextField ftxPassword;

    //Instance Variables
    private char[] password;
    private String passwordString;
    private int handler;

    //Default Constructor
    public OSD()
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        password = null;
        passwordString = "";
        handler = 0;

        for(char t : password)
            passwordString += '*';
        ftxPassword.setText(passwordString);

        /*btnHide.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);

                if(handler == 0)
                {
                    ftxPassword.setText(new String(password));
                    handler = 1;
                    btnHide.setText("Hide Password");
                }
                else
                {
                    ftxPassword.setText(passwordString);
                    handler = 0;
                    btnHide.setText("Unhide Password");
                }
            }
        });*/
    }
    //Overloaded Constructor
    public OSD(char[] passwordPar)
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        password = passwordPar;
        passwordString = "";
        handler = 0;

        for(char t : password)
            passwordString += '*';
        ftxPassword.setText(passwordString);

        btnHide.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);

                if(handler == 0)
                {
                    ftxPassword.setText(new String(password));
                    handler = 1;
                    btnHide.setText("Hide Password");
                }
                else
                {
                    ftxPassword.setText(passwordString);
                    handler = 0;
                    btnHide.setText("Unhide Password");
                }
            }
        });
    }


    private void onOK()
    {
        StringSelection stringSelection = new StringSelection(new String(password));
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);

        dispose();
    }

    private void onCancel()
    {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args)
    {
        OSD dialog = new OSD();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
