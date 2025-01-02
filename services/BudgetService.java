package services;
import models.Budget;
import java.util.ArrayList;
import java.util.List;

public class BudgetService {
    private List<Budget> budgets;
    public BudgetService(){
        this.budgets = new ArrayList<>();

    }

    public void addBudget(String category, double limit){
        budgets.add(new Budget(category, limit));
    }

    public Budget findBudget(String category){
        for(Budget b: budgets){
            if (b.getCategory().equalsIgnoreCase(category)){
                return b;
            }
        }
        return null;
    }

    public List<Budget> getAllBudgets(){
        return budgets; 
    }

}
