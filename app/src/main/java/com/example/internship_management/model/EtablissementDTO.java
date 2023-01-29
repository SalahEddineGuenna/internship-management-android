package com.example.internship_management.model;


public class EtablissementDTO {
    private Long id;
    private String name;

    private String mail;

    private String address;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String address) {
        this.address = address;
    }

    public ResponsableStageDTO getResponsableStage() {
        return responsableStage;
    }

    public void setResponsableStage(ResponsableStageDTO responsableStage) {
        this.responsableStage = responsableStage;
    }

    private ResponsableStageDTO responsableStage;
    //private List<EtudiantDTO> etudiantDTOList;

    public ResponsableStageDTO getResponsableStageDTO() {
        return responsableStage;
    }

    public void setResponsableStageDTO(ResponsableStageDTO responsableStage) {
        this.responsableStage = responsableStage;
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
}
