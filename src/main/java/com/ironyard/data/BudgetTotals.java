package com.ironyard.data;

/**
 * Created by nathanielellsworth on 10/11/16.
 */
public class BudgetTotals { String category;
    double budgetTotal;
    double actualTotal;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getBudgetTotal() {
        return budgetTotal;
    }

    public void setBudgetTotal(double budgetTotal) {
        this.budgetTotal = budgetTotal;
    }

    public double getActualTotal() {
        return actualTotal;
    }

    public void setActualTotal(double actualTotal) {
        this.actualTotal = actualTotal;
    }
}

