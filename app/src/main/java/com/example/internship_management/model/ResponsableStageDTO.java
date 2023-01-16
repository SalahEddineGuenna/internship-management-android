package com.example.internship_management.model;


public class ResponsableStageDTO extends UtilisateurDTO{
    private EtablissementDTO etablissementDTOS;

    public EtablissementDTO getEtablissementDTOS() {
        return etablissementDTOS;
    }

    public void setEtablissementDTOS(EtablissementDTO etablissementDTOS) {
        this.etablissementDTOS = etablissementDTOS;
    }


}
