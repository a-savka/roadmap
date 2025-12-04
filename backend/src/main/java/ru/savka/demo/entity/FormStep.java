package ru.savka.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "form_steps")
public class FormStep {

    @EmbeddedId
    private FormStepId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("formId")
    @JoinColumn(name = "form_id")
    private Form form;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stepName")
    @JoinColumn(name = "step_name")
    private Step step;

    private int completed; // 0 or 1

    // Getters and Setters

    public FormStepId getId() {
        return id;
    }

    public void setId(FormStepId id) {
        this.id = id;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
