//import required classes and packages  

import DB.DbFunctions;
import DB.User;
import Logining.Password;
import Logining.SendMail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import static Logining.Password.PasswordValidation;

//create NewPage class to create a new page on which user will navigate  

class LoginFailPage extends JFrame implements ActionListener {
    JPanel pane;
    JLabel emailLabel, codeLabel;
    JButton emailSubmit, codeSubmit;
    JTextField emailField, confirmationCodeField;

    LoginFailPage() {

        emailLabel = new JLabel("Email");
        emailField = new JTextField(20);
        emailSubmit = new JButton(String.format("Send code"));

        codeLabel = new JLabel("Code");
        confirmationCodeField = new JTextField(20);
        codeSubmit = new JButton("Reset Password");


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Password recovering");
        setSize(255, 230);

        pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pane.add(emailLabel);
        pane.add(emailField);
        pane.add(emailSubmit);

        pane.add(codeLabel);
        pane.add(confirmationCodeField);
        pane.add(codeSubmit);

        add(pane, BorderLayout.CENTER);

        emailSubmit.addActionListener(this);
        codeSubmit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connect_to_db("Users", "postgres", "1423");
        String confirmationCode = Password.getCode();
        if (e.getSource() == emailSubmit) {
            String emailValue = emailField.getText();
            SendMail mail = new SendMail(("Confirmation code: " + confirmationCode), emailValue);
        }
        if (e.getSource() == codeSubmit) {
            if (confirmationCode.equals(confirmationCodeField.getText())) {
                JFrame passwordRecoverPage = new passwordRecoverPage();
                passwordRecoverPage.setVisible(true);
            }
            else {
                System.out.println("Err");
            }
        }

    }
}


class passwordRecoverPage extends JFrame implements ActionListener {
    JPanel panel;
    JLabel newPasswordLabel, repeatPasswordLabel, successReset;
    JTextField newPasswordField, repeatPasswordField;
    JButton submitPasswordBtn;

    passwordRecoverPage() {

        newPasswordLabel = new JLabel("New password");
        newPasswordField = new JPasswordField(20);

        repeatPasswordLabel = new JLabel("RepeatPassword");
        repeatPasswordField = new JPasswordField(20);

        submitPasswordBtn = new JButton("Set new Password");

        successReset = new JLabel("Password updated! Close the window");
        successReset.setVisible(false);
        successReset.setForeground(Color.green);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Password recovering");
        setSize(255, 230);

        panel = new JPanel(new FlowLayout(40));
        panel.add(newPasswordLabel);
        panel.add(newPasswordField);
        panel.add(repeatPasswordLabel);
        panel.add(repeatPasswordField);
        panel.add(successReset);
        panel.add(submitPasswordBtn);

        add(panel, BorderLayout.CENTER);


        submitPasswordBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitPasswordBtn) {
            String newPasswordSubmited = newPasswordField.getText();

            if (PasswordValidation(newPasswordSubmited)) {
                successReset.setVisible(true);
            }
        }
    }
}