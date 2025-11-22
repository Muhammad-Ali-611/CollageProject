package monthlybudget;

import java.util.Scanner;

public class MonthlyBudgetTracker {

    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard
        Scanner input = new Scanner(System.in);

        // Declare variables to store income and different expense categories
        double income;
        double rent;
        double food;
        double transport;
        double other;

        // Declare variables for total expenses, remaining balance, and suggested savings
        double totalExpenses;
        double remaining;
        double suggestedSavings;

        // Ask the user for their monthly income
        System.out.print("Enter your monthly income: ");
        income = input.nextDouble();

        // Ask the user for rent or housing cost
        System.out.print("Enter your monthly rent or housing cost: ");
        rent = input.nextDouble();

        // Ask the user for food cost
        System.out.print("Enter your monthly food cost: ");
        food = input.nextDouble();

        // Ask the user for transportation cost
        System.out.print("Enter your monthly transportation cost: ");
        transport = input.nextDouble();

        // Ask the user for other expenses
        System.out.print("Enter your other monthly expenses: ");
        other = input.nextDouble();

        // Calculate total expenses by adding all categories together
        totalExpenses = rent + food + transport + other;

        // Calculate remaining balance by subtracting expenses from income
        remaining = income - totalExpenses;

        // Display the total expenses and remaining balance to the user
        System.out.println("\n=== Monthly Budget Summary ===");
        System.out.println("Total monthly income: " + income);
        System.out.println("Total monthly expenses: " + totalExpenses);
        System.out.println("Remaining balance: " + remaining);

        // If the remaining balance is positive, suggest a savings amount (20% of remaining)
        if (remaining > 0) {
            suggestedSavings = remaining * 0.20;
            System.out.println("You have money left over this month.");
            System.out.println("Suggested savings (20% of remaining): " + suggestedSavings);
        }
        // If the remaining balance is exactly zero, inform the user they are breaking even
        else if (remaining == 0) {
            System.out.println("You are breaking even this month.");
            System.out.println("You do not have extra money to save.");
        }
        // If the remaining balance is negative, warn the user about overspending
        else {
            // Use Math.abs to show the overspending amount as a positive number
            System.out.println("You are overspending this month by: " + Math.abs(remaining));
            System.out.println("Consider reducing your expenses or increasing your income.");
        }

        // Close the Scanner to free system resources
        input.close();
    }
}
