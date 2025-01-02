package models;
public class Budget {
    private String category;
    private double limit;
    private double spent;

    public Budget(String category, double limit){
        this.category = category;
        this.limit = limit;
        this.spent = 0;


    }

    public void addExpense(double amount){
        this.spent+= amount;

    }

    public double remainingBudget(){
        return this.limit - this.spent;

    }

    public String getCategory(){
        return category;

    }
    public double getSpent(){
        return spent;

    }
    public double getLimit(){
        return limit;
    }

}