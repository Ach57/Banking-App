package main;

import javax.management.RuntimeErrorException;
import javax.swing.*;

import main.utils.Encryption;
import main.utils.FileUtils;

import java.awt.*;
import java.awt.datatransfer.FlavorListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.nio.file.*;

public class LoginScreen extends JFrame implements Encryption, FileUtils {

    public LoginScreen() {
        setTitle("Banking App - Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.BLUE);
        userLabel.setFont(new Font("Arial",Font.BOLD, 14));
        userLabel.setBorder(BorderFactory.createEmptyBorder(10,10,5,5));

        JTextField userField = new JTextField(15);
        
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.BLUE);
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 5));


        JPasswordField passField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Add components to panel
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel, BorderLayout.CENTER);

        // Login button action
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (authenticateUser(username, password)){
                JOptionPane.showMessageDialog(null, "Login Successful!");
                dispose();
                Dashboard dashboard = new Dashboard(username);
                dashboard.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Register button action
        registerButton.addActionListener(e -> {
            dispose();
            RegistrationScreen registrationScreen = new RegistrationScreen();
            registrationScreen.setVisible(true);
        });
    }

    @Override
    public boolean authenticateUser(String username, String password){
        Path filePath = Paths.get("./data/users.txt");

        try{
            for (String line: Files.readAllLines(filePath)){
                String [] userDetails = line.split(",");
                if (userDetails.length ==2){
                    String storedUser = userDetails[0];
                    String storedPassword = userDetails[1];
                    if (storedUser.equals(username)){
                        String encryptedPass = encryptPassword(password);
                        return encryptedPass.equals(storedPassword);
                    }
                }

            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;

    }

    @Override
    public String encryptPassword(String password) throws Exception { //PasswordEncryption
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception e){
            throw new RuntimeException("Error encrypting password", e);
        }
        
    }
}
