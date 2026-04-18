package ru.savka.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "forms")
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    @JsonBackReference
    private User user;

    private LocalDate entryDate;

    @ManyToOne
    @JoinColumn(name = "citizenship_country_code", referencedColumnName = "code")
    private Country citizenshipCountry;

    private String visitPurpose; // "work", "recreation"
    private String relocationProgramStatus; // "yes", "no", "family"
    private String hqsStatus; // "yes", "no", "family"

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Country getCitizenshipCountry() {
        return citizenshipCountry;
    }

    public void setCitizenshipCountry(Country citizenshipCountry) {
        this.citizenshipCountry = citizenshipCountry;
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
