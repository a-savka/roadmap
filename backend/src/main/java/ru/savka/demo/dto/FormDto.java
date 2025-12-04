package ru.savka.demo.dto;

import java.time.LocalDate;

public class FormDto {

    private String username;
    private LocalDate entryDate;
    private String citizenshipCountryCode;
    private String visitPurpose;
    private String relocationProgramStatus;
    private String hqsStatus;

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getCitizenshipCountryCode() {
        return citizenshipCountryCode;
    }

    public void setCitizenshipCountryCode(String citizenshipCountryCode) {
        this.citizenshipCountryCode = citizenshipCountryCode;
    }

    public String getVisitPurpose() {
        return visitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
    }

    public String getRelocationProgramStatus() {
        return relocationProgramStatus;
    }

    public void setRelocationProgramStatus(String relocationProgramStatus) {
        this.relocationProgramStatus = relocationProgramStatus;
    }

    public String getHqsStatus() {
        return hqsStatus;
    }

    public void setHqsStatus(String hqsStatus) {
        this.hqsStatus = hqsStatus;
    }
}
