package ru.savka.demo.dto;

public class ValidationRuleDto {

    private Long id;
    private String countryCode;
    private String visitPurpose;
    private String relocationProgramStatus;
    private String hqsStatus;
    private int maxDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }
}
