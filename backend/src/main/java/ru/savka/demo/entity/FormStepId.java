package ru.savka.demo.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FormStepId implements Serializable {

    private Long formId;
    private String stepName;

    // Getters, Setters, equals, and hashCode

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormStepId that = (FormStepId) o;
        return Objects.equals(formId, that.formId) && Objects.equals(stepName, that.stepName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formId, stepName);
    }
}
