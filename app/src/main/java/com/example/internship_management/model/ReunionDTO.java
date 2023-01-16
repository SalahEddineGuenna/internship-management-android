package com.example.internship_management.model;

import java.time.LocalDateTime;

public class ReunionDTO {
    private Long id;
    private LocalDateTime dateReunion;

    private Student etudiantDTO;

    private ProfesseurDTO professeurDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateReunion() {
        return dateReunion;
    }

    public void setDateReunion(LocalDateTime dateReunion) {
        this.dateReunion = dateReunion;
    }

    public Student getEtudiantDTO() {
        return etudiantDTO;
    }

    public void setEtudiantDTO(Student etudiantDTO) {
        this.etudiantDTO = etudiantDTO;
    }

    public ProfesseurDTO getProfesseurDTO() {
        return professeurDTO;
    }

    public void setProfesseurDTO(ProfesseurDTO professeurDTO) {
        this.professeurDTO = professeurDTO;
    }
}
