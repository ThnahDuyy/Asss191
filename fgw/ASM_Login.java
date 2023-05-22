package ASM.fgw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ASM_Login extends JFrame
{
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JPanel mainPanel;

    public ASM_Login(String title) throws HeadlessException{
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void Login(){
        String usr = txtUsername.getText();
        String pwd = txtPassword.getText();
        if(usr.equals("admin") && pwd.equals("123")){
            JOptionPane.showMessageDialog(this,"Welcome to admin");
//            JOptionPane.showMessageDialog(this, "Hello" + txtUsername.getText());
//            this.setVisible(fales);
            JFrame c = new Management("Management");
            c.setLocationRelativeTo(null);
            c.setVisible(true);

        }else{
            JOptionPane.showMessageDialog(this,"Wrong Username or Password");
        }
    }

    public static void main(String[] args) {
        JFrame c = new ASM_Login("Login to table Management");
        c.setSize(600,250);
        c.setLocationRelativeTo(null);
        c.setVisible(true);

    }


}
