package main;

import models.Budget;
import services.BudgetService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BudgetToolDialog extends JDialog {
    private BudgetService budgetService;
    private JTextArea budgetDisplayArea;

    public BudgetToolDialog(JFrame parent) {
        super(parent, "Budgeting Tool", true);
        budgetService = new BudgetService();

        setSize(400, 400);
        setLayout(new BorderLayout());

        // Display area for budgets
        budgetDisplayArea = new JTextArea();
        budgetDisplayArea.setEditable(false);
        add(new JScrollPane(budgetDisplayArea), BorderLayout.CENTER);

        // Buttons for actions
        JButton addBudgetButton = new JButton("Add Budget");
        JButton addExpenseButton = new JButton("Add Expense");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBudgetButton);
        buttonPanel.add(addExpenseButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addBudgetButton.addActionListener(e -> {
            String category = JOptionPane.showInputDialog(this, "Enter category name:");
            String limitStr = JOptionPane.showInputDialog(this, "Enter budget limit:");
            if (category != null && limitStr != null) {
                try {
                    double limit = Double.parseDouble(limitStr);
                    budgetService.addBudget(category, limit);
                    updateBudgetDisplay();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid limit!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addExpenseButton.addActionListener(e -> {
            String category = JOptionPane.showInputDialog(this, "Enter category name:");
            String expenseStr = JOptionPane.showInputDialog(this, "Enter expense amount:");
            if (category != null && expenseStr != null) {
                try {
                    double amount = Double.parseDouble(expenseStr);
                    Budget budget = budgetService.findBudget(category);
                    if (budget != null) {
                        budget.addExpense(amount);
                        updateBudgetDisplay();
                    } else {
                        JOptionPane.showMessageDialog(this, "Category not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateBudgetDisplay();
    }

    private void updateBudgetDisplay() {
        StringBuilder displayText = new StringBuilder("Budgets:\n");
        for (Budget b : budgetService.getAllBudgets()) {
            displayText.append(b.getCategory())
                       .append(" - Limit: $").append(b.getLimit())
                       .append(", Spent: $").append(b.getSpent())
                       .append(", Remaining: $").append(b.remainingBudget())
                       .append("\n");
        }
        budgetDisplayArea.setText(displayText.toString());
    }
}
