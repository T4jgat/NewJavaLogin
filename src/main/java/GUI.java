import DB.DbFunctions;
import DB.User;
import VkNotification.MyTelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Exception;
import java.sql.Connection;

import static Logining.MainMenu.isNumeric;
import static Logining.Password.doHashing;

class CreateLoginForm extends JFrame {
    int totalAttempts = 3;
    //initialize button, panel, label, and text field
    JButton loginBtn;
    JPanel newPanel;
    JLabel userLabel, passLabel;
    JTextField textField1, textField2;
    JButton forgetPass;
    JLabel incorrectDataMessage, attemptCount;
    String chatId, message;

    //calling constructor
    CreateLoginForm(Connection conn) {
        //create label for username
        userLabel = new JLabel();
        userLabel.setText("Login (Email/ID)");//set label value for textField1

        //create text field to get username from the user
        textField1 = new JTextField(20);    //set length of the text

        //create label for password
        passLabel = new JLabel();
        passLabel.setText("Password");      //set label value for textField2

        //create text field to get password from the user
        textField2 = new JPasswordField(20);    //set length for the password

        //create submit button
        loginBtn = new JButton("SUBMIT"); //set label to button

        forgetPass = new JButton("Forget password?");

        incorrectDataMessage = new JLabel("");
        incorrectDataMessage.setVisible(false);
        attemptCount = new JLabel("");
        attemptCount.setVisible(false);


        //create panel to put form elements
        newPanel = new JPanel(new FlowLayout(40));
        newPanel.add(userLabel);    //set username label to panel
        newPanel.add(textField1);   //set text field to panel
        newPanel.add(passLabel);    //set password label to panel
        newPanel.add(textField2);   //set text field to panel
        newPanel.add(loginBtn);           //set button to panel
        newPanel.add(forgetPass);
        newPanel.add(incorrectDataMessage);
        newPanel.add(attemptCount);

        //set border to panel
        add(newPanel, BorderLayout.CENTER);


        //perform action on button click
        loginBtn.addActionListener(e -> {
            String userValue = textField1.getText();        //get user entered username from the textField1
            String passValue = textField2.getText();
            if (totalAttempts != 1) {
                if (isNumeric(userValue)) {
                    int IdLogin = Integer.parseInt(userValue); // data type substitution for loginType
                    User UserById = new User(conn, IdLogin);
                    if (doHashing(passValue).equals(UserById.getPassword()) && IdLogin == UserById.getId_num()) {
                        newPanel.removeAll();
                        newPanel.add(new JLabel("Welcome: " + UserById.getName()));
                        newPanel.revalidate();
                        newPanel.repaint();

                        chatId = "YOUR CHAT ID"; // User chatId
                        message = "Someone logged in!"; // Replace with the actual message to send
                        MyTelegramBot bot = new MyTelegramBot(chatId, message);
                        TelegramBotsApi botsApi = null;
                        try {
                            botsApi = new TelegramBotsApi(DefaultBotSession.class);
                            botsApi.registerBot(bot);
                            bot.sendMessage(); // Send the message immediately after starting the bot
                        } catch (TelegramApiException ee) {
                            ee.printStackTrace();
                        }

                    } else {
                        totalAttempts--;
                        incorrectDataMessage.setVisible(true);
                        attemptCount.setVisible(true);
                        incorrectDataMessage.setText("Incorrect login or password.");
                        attemptCount.setText("You have " + totalAttempts + " try(-ies)");
                        incorrectDataMessage.setForeground(Color.red);
                        attemptCount.setForeground(Color.red);
                    }
                } else if (!isNumeric(userValue)) {
                    String emailLogin = userValue;
                    User UserByEmail = new User(conn, emailLogin);
                    if (doHashing(passValue).equals(UserByEmail.getPassword()) && emailLogin.equals(UserByEmail.getEmail())) {
                        newPanel.removeAll();
                        newPanel.add(new JLabel("Welcome: " + UserByEmail.getName()));
                        newPanel.revalidate();
                        newPanel.repaint();
                    } else {
                        totalAttempts--;
                        incorrectDataMessage.setVisible(true);
                        incorrectDataMessage.setText("Incorrect login or password");
                        attemptCount.setVisible(true);
                        attemptCount.setText("You have " + totalAttempts + " try(-ies)");
                        incorrectDataMessage.setForeground(Color.red);
                        attemptCount.setForeground(Color.red);
                    }
                }
            } else {
                loginBtn.setEnabled(false);
                attemptCount.setVisible(false);
                incorrectDataMessage.setText("Maximum number of attempts exceeded");
            }
        });//add action listener to button
        forgetPass.addActionListener(e -> {
            JFrame passRecover = new LoginFail();
        });//add action listener to button
        setTitle("LOGIN FORM");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

//create the main class
class LoginFormDemo {
    //main() method start
    public static void main(String arg[]) {
        try {
            DbFunctions db = new DbFunctions();
            Connection conn = db.connect_to_db("Users", "postgres", "1423");
            //create instance of the CreateLoginForm
            CreateLoginForm form = new CreateLoginForm(conn);
            form.setSize(255, 230);  //set size of the frame
            form.setVisible(true);  //make form visible to the user
        } catch (Exception e) {
            //handle exception
        }
    }
}