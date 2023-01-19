package com.example.internship_management.model;

import java.time.LocalDateTime;
import java.util.Date;

public class ReunionDTO {
    private Long id;
    private String dateReunion;

    private Student etudiant;

    private ProfesseurDTO professeur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateReunion() {
        return dateReunion;
    }

    public void setDateReunion(String dateReunion) {
        this.dateReunion = dateReunion;
    }

    public Student getEtudiantDTO() {
        return etudiant;
    }

    public void setEtudiantDTO(Student etudiant) {
        this.etudiant = etudiant;
    }

    public ProfesseurDTO getProfesseurDTO() {
        return professeur;
    }

    public void setProfesseurDTO(ProfesseurDTO professeur) {
        this.professeur = professeur;
    }
}
