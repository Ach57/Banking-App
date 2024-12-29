package main;

import javax.swing.*;

import main.utils.Encryption;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.*;
import java.security.MessageDigest;

public class RegistrationScreen extends JFrame implements Encryption {

    public RegistrationScreen() {
        setTitle("Banking App - Register");
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
        JButton registerButton = new JButton("Register");
        JButton goBack = new JButton("Back to Login");

        // Add components to panel
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(goBack);
        panel.add(registerButton);
        

        add(panel, BorderLayout.CENTER);

        // Register button action
        registerButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Encrypt password
                String encryptedPassword = encryptPassword(password);

                // Save to file
                Path filePath = Paths.get("./data/users.txt");
                String userData = username + "," + encryptedPassword;
                Files.write(filePath, (userData + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);

                JOptionPane.showMessageDialog(null, "Registration Successful!");
                dispose();
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        goBack.addActionListener(e->{

            dispose();
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }

    @Override
    public String encryptPassword(String password) throws Exception {
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
