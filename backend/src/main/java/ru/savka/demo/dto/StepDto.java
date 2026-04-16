package ru.savka.demo.dto;

import java.util.List;

public class StepDto {

    private String stepName;
    private String stepDescription;
    private int stepOrder;
    private boolean enabled;
    private Integer deadlineDays;
    private List<StepConditionDto> conditions;

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getDeadlineDays() {
        return deadlineDays;
    }

    public void setDeadlineDays(Integer deadlineDays) {
        this.deadlineDays = deadlineDays;
    }

    public List<StepConditionDto> getConditions() {
        return conditions;
    }

    public void setConditions(List<StepConditionDto> conditions) {
        this.conditions = conditions;
    }
}
