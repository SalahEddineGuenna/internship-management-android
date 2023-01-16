package com.example.internship_management.model;


import java.util.Date;

public class DocumentDTO {
    private Long id;
    private String name;
    private Date dateSoumission;

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

    public Date getDateSoumission() {
        return dateSoumission;
    }

    public void setDateSoumission(Date dateSoumission) {
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
