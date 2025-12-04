package ru.savka.demo.dto;

public class ValidationResultDto {
    private boolean overdue;
    private int overdueDays;

    public ValidationResultDto(boolean overdue, int overdueDays) {
        this.overdue = overdue;
        this.overdueDays = overdueDays;
    }

    // Getters and setters
    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }
}
