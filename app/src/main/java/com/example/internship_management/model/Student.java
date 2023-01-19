package com.example.internship_management.model;

import com.example.internship_management.enums.Role;

import java.util.List;

public class Student extends UtilisateurDTO {

    private String niveau;
    private List<DocumentDTO> documents;
    private ProfesseurDTO encadrant;
    private EtablissementDTO etablissement;
    private ReunionDTO reunion;

    public ReunionDTO getReunionDTO() {
        return reunion;
    }

    public void setReunionDTO(ReunionDTO reunion) {
        this.reunion = reunion;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public List<DocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDTO> documents) {
        this.documents = documents;
    }

    public ProfesseurDTO getEncadrant() {
        return encadrant;
    }

    public void setEncadrant(ProfesseurDTO encadrant) {
        this.encadrant = encadrant;
    }

    public EtablissementDTO getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(EtablissementDTO etablissement) {
        this.etablissement = etablissement;
    }

    @Override
    public String toString() {
        return "Student{" +
                "niveau='" + niveau + '\'' +
                ", documents=" + documents +
                ", encadrant=" + encadrant +
                ", etablissement=" + etablissement +
                '}';
    }
}
