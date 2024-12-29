package models;

public class Account {
    private String username;
    private double balance;

    public Account(String username, double balance){
        this.username = username;
        this.balance = balance;

    }

    public String getUserName(){
        return username;
    }
    public void setUserName(String newUser){
        this.username = newUser;
    }
    public double getBalance(){
        return balance;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
    @Override
    public String toString(){
        return username+","+balance;
    }

    
}
