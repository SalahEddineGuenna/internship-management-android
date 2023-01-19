package com.example.internship_management.model;


public class ResponsableStageDTO extends UtilisateurDTO{
    private EtablissementDTO etablissement;

    public EtablissementDTO getEtablissementDTOS() {
        return etablissement;
    }

    public void setEtablissementDTOS(EtablissementDTO etablissement) {
        this.etablissement = etablissement;
    }


}
