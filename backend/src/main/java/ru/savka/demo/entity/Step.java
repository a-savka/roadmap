package ru.savka.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "steps")
public class Step {

    @Id
    private String stepName;

    private String stepDescription;

    private int stepOrder;

    private boolean enabled;

    private Integer deadlineDays;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<StepCondition> conditions = new ArrayList<>();

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

    public List<StepCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<StepCondition> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(StepCondition condition) {
        conditions.add(condition);
        condition.setStep(this);
    }

    public void removeCondition(StepCondition condition) {
        conditions.remove(condition);
        condition.setStep(null);
    }
}
