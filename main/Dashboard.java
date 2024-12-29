package main;

import javax.swing.*;

import services.AccountService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {

    // Constructor to set up the UI
    public Dashboard(String username) {
        // Frame settings
        setTitle("Banking App - Dashboard");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        add(welcomeLabel, BorderLayout.NORTH);

        // Buttons for actions
        JButton createAccountButton = new JButton("Create Account");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton viewBalanceButton = new JButton("View Balance");

        // Add buttons to a panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.add(createAccountButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(viewBalanceButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Status area
        JTextArea statusArea = new JTextArea(5, 30);
        statusArea.setEditable(false);
        add(new JScrollPane(statusArea), BorderLayout.SOUTH);


        viewBalanceButton.addActionListener(e ->{
            AccountService accountService = new AccountService();
            double balance = accountService.getBalance(username);
            statusArea.append("Your current balance is $"+ balance+"\n");

        });

        createAccountButton.addActionListener(e->{
            String user = JOptionPane.showInputDialog(this, "Enter your username:");
            String balanceStr = JOptionPane.showInputDialog(this, "Enter initial balance:");

            if (user !=null && balanceStr !=null){
                try{
                    double initialBalance = Double.parseDouble(balanceStr);
                    AccountService accountService = new AccountService();
                    if( accountService.createAccount(user, initialBalance)){
                        statusArea.append("Account created for "+ user+" with balance "+ initialBalance+"\n");

                    }else{
                        statusArea.append("Failed to create account. Username may already exist.\n");
                    }
                }catch(NumberFormatException ex){
                    statusArea.append("Invalid balance input.\n");  
                }
            }


        });
       
    }

}
