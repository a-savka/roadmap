package ru.savka.demo.dto;

import java.util.List;

public class ValidationResultDto {
    private boolean overdue;
    private int overdueDays;
    private List<StepDto> applicableSteps;

    public ValidationResultDto(boolean overdue, int overdueDays) {
        this.overdue = overdue;
        this.overdueDays = overdueDays;
    }

    public ValidationResultDto(boolean overdue, int overdueDays, List<StepDto> applicableSteps) {
        this.overdue = overdue;
        this.overdueDays = overdueDays;
        this.applicableSteps = applicableSteps;
    }

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

    public List<StepDto> getApplicableSteps() {
        return applicableSteps;
    }

    public void setApplicableSteps(List<StepDto> applicableSteps) {
        this.applicableSteps = applicableSteps;
    }
}
