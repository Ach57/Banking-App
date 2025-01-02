package main;

import javax.swing.*;

import models.Account;
import services.AccountService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {

    // Constructor to set up the UI
    public Dashboard(String username) {
        // Frame settings
        setTitle("Banking App - Dashboard");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        add(welcomeLabel, BorderLayout.NORTH);

        // Buttons for actions
        JButton createAccountButton = new JButton("Create Account");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton viewBalanceButton = new JButton("View Balance");
        JButton logoutButton = new JButton("Logout");
        JButton interestCalculatorButton = new JButton("Interest Calculator");
        JButton budgetToolButton = new JButton("Budgeting Tool");
        

        // Add buttons to a panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.add(createAccountButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(viewBalanceButton);
        buttonPanel.add(interestCalculatorButton); 
        buttonPanel.add(budgetToolButton);
        add(buttonPanel, BorderLayout.CENTER);


        JTextArea statusArea = new JTextArea(8, 30);
        statusArea.setEditable(false);
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());

        // Add the status area
        JScrollPane scrollPane = new JScrollPane(statusArea);
        southPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the logout button
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoutPanel.add(logoutButton);
        southPanel.add(logoutPanel, BorderLayout.SOUTH);

        // Add the combined panel to the SOUTH region
        add(southPanel, BorderLayout.SOUTH);


        viewBalanceButton.addActionListener(e ->{
            String user = JOptionPane.showInputDialog(this, "Enter your username:");
            AccountService accountService = new AccountService();
            double balance = accountService.getBalance(user);
            if (balance ==-1) statusArea.append("Account not found.\n");
            else statusArea.append("Your current balance is $"+ balance+"\n");

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

        depositButton.addActionListener(e->{
            String user = JOptionPane.showInputDialog(this, "Enter the account name:");
            String depositStr = JOptionPane.showInputDialog(this, "Enter deposit amount:");
            if(depositStr != null){
                try{
                    double amount = Double.parseDouble(depositStr);
                    AccountService accountService = new AccountService();
                    if(accountService.deposit(user, amount)){
                        statusArea.append("Deposit successful! " + amount+ " added to "+ user +" 's account.\n");
                    }else{
                        statusArea.append("Failed to deposit. Ensure the username exists and amount is valid.\n");
                    }
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Invalid amount entered.\n","Error",JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        withdrawButton.addActionListener(e->{
            String user = JOptionPane.showInputDialog(this, "Enter the user name:");
            String withdrawStr = JOptionPane.showInputDialog(this, "Enter the amount:");

            if(withdrawStr != null){
                try{
                    double amount = Double.parseDouble(withdrawStr);
                    AccountService accountService = new AccountService();
                    if(accountService.withdraw(user, amount, statusArea)){
                        statusArea.append("Withdraw successful! "+ amount + " removed from user: "+user+"'s account.\n");
                    }else{
                        statusArea.append("Failed to withdraw the money. Ensure the username exists and amount is valid.\n");
                    }

                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Invalid amount entered.\n", "Error",JOptionPane.ERROR_MESSAGE);

                }
            }

        });

        logoutButton.addActionListener(e->{
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);


            if (response == JOptionPane.YES_OPTION){
                dispose();
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
            }

            

        });

        interestCalculatorButton.addActionListener(e->{
            InterestCalculatorDialog calculatorDialog = new InterestCalculatorDialog(this);
            calculatorDialog.setVisible(true);

        });

        budgetToolButton.addActionListener(e->{
            BudgetToolDialog budgetDialog = new BudgetToolDialog(this);
            budgetDialog.setVisible(true);
        }); 
       
    }

}
