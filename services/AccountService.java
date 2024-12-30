package services;
import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import models.Account;

public class AccountService {
    private static final String ACCOUNTS_FILE = "./data/accounts.txt";
    /**
     * 
     * @param username type String
     * @return double balance
     */
    public double getBalance(String username){ 
        try(BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))){
            String line;
            while ((line= reader.readLine()) != null){
                String [] parts = line.split(",");
                if ( parts[0].equals(username)){
                    return Double.parseDouble(parts[1]);
                }
            }
            return -1;
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error reading Account file"+ e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        } catch( NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid balance format in accounts file.");
        }
        return 0.0;
    }
    /**
     * 
     * @param username
     * @param initialBalance
     * @return
     */
    public boolean createAccount(String username, double initialBalance){
        if (getBalance(username)>0){
            JOptionPane.showMessageDialog(null, "Account already exists", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Account newAccount = new Account(username, initialBalance);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE, true))){
            writer.write(newAccount.toString());
            writer.newLine();
            return true;
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error writing to account file: "+ e.getMessage());
        }

        return false;
    }

    public boolean deposit(String username, double amount){
        if(amount<=0){
            return false;
        }
        ArrayList <Account> accounts = new ArrayList<>();
        boolean userFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))){
            String line;
            while((line = reader.readLine())!=null){
                String[] parts = line.split(",");
                String user = parts[0];
                double balance = Double.parseDouble(parts[1]);
                if(user.equals(username)){
                    balance+=amount;
                    userFound = true;

                }
                accounts.add(new Account(user, balance));
            }
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Error reading accounts file "+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
            return false;
        }

        if(!userFound){
            return false;
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))){
            for (Account account: accounts){
                writer.write(account.toString());
                writer.write("\n");
            }
            return true;

        } catch(IOException IO){
            JOptionPane.showMessageDialog(null, "Error reading account file "+ IO.getMessage());
        }

        return false;
    }

    public boolean withdraw(String username, double amount, JTextArea statusArea){
        if (amount <= 0) {
            return false;
        }
        ArrayList<Account> accounts = new ArrayList<>();
        boolean userFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String user = parts[0];
                double balance = Double.parseDouble(parts[1]);
    
                if (user.equals(username)) {
                    if (balance >= amount) {
                        balance -= amount; // Deduct the amount
                        userFound = true;
                    } else {
                        statusArea.append("Insufficient funds for user: "+ username+"\n");
                        return false;
                    }
                }
                accounts.add(new Account(user, balance)); // Use the correct username from file
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error reading account file "+ ex.getMessage());
            return false;
        }
    
        if (!userFound) {
            statusArea.append("User not found: "+ username);
            return false;
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account account : accounts) {
                writer.write(account.toString());
                writer.newLine();
            }
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error writing account file: "+ ex.getMessage());
            return false;
        }

    }

}
