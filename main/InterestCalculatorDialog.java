package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InterestCalculatorDialog extends JDialog {
    public InterestCalculatorDialog(JFrame parent) {
        super(parent, "Interest Rate Calculator", true);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.add(new JLabel("Principal Amount:"));
        JTextField principalField = new JTextField();
        inputPanel.add(principalField);

        inputPanel.add(new JLabel("Annual Interest Rate (%):"));
        JTextField rateField = new JTextField();
        inputPanel.add(rateField);

        inputPanel.add(new JLabel("Time Period (years):"));
        JTextField timeField = new JTextField();
        inputPanel.add(timeField);

        inputPanel.add(new JLabel("Compounding Frequency:"));
        JTextField frequencyField = new JTextField("1"); // Default to yearly compounding
        inputPanel.add(frequencyField);

        add(inputPanel, BorderLayout.CENTER);

        JButton calculateButton = new JButton("Calculate");
        JTextArea resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);

        calculateButton.addActionListener((ActionEvent e) -> {
            try {
                double principal = Double.parseDouble(principalField.getText());
                double rate = Double.parseDouble(rateField.getText());
                double time = Double.parseDouble(timeField.getText());
                int frequency = Integer.parseInt(frequencyField.getText());

                double compoundInterest = principal * Math.pow(1 + (rate / (frequency * 100)), frequency * time) - principal;
                double totalAmount = principal + compoundInterest;

                resultArea.setText(String.format("Compound Interest: $%.2f\nTotal Amount: $%.2f", compoundInterest, totalAmount));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(calculateButton, BorderLayout.NORTH);
        southPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(southPanel, BorderLayout.SOUTH);
    }
}
