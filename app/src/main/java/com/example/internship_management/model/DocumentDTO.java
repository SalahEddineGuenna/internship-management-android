package com.example.internship_management.model;


import java.util.Date;

public class DocumentDTO {
    private Long id;
    private String name;
    private String dateSoumission;
    private String dateModification;
    private Student student;

    public String getDateModification() {
        return dateModification;
    }

    public void setDateModification(String dateModification) {
        this.dateModification = dateModification;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateSoumission() {
        return dateSoumission;
    }

    public void setDateSoumission(String dateSoumission) {
        this.dateSoumission = dateSoumission;
    }

    @Override
    public String toString() {
        return "DocumentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateSoumission=" + dateSoumission +
                '}';
    }
}
