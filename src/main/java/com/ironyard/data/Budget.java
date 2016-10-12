package com.ironyard.data;

/**
 * Created by nathanielellsworth on 10/11/16.
 */
public class Budget {
    private long id;
    private String description;
    private String category;
    private double budgetAmount;
    private double actualAmount;

    public Budget(long id, String description, String category, double budgetAmount, double actualAmount) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.budgetAmount = budgetAmount;
        this.actualAmount = actualAmount;
    }

    public Budget() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
    }
}

