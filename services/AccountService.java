package services;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

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
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error reading Account file"+ e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        } catch( NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid balance format in accounts file.");
        }


        return 0.0;
    }

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
}
