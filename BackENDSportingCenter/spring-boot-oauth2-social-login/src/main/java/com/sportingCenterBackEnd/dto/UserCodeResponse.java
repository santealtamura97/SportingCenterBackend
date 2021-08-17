package com.sportingCenterBackEnd.dto;

import lombok.Value;

@Value
public class UserCodeResponse {
    private String displayName;
    private String idAbbonamento;
    private String scadenzaAbbonamento;
    private Long ingressi;

    public  UserCodeResponse(String displayName, String idAbbonamento, String scadenzaAbbonamento, Long ingressi) {
        this.displayName = displayName;
        this.idAbbonamento = idAbbonamento;
        this.scadenzaAbbonamento = scadenzaAbbonamento;
        this.ingressi = ingressi;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIdAbbonamento() {
        return idAbbonamento;
    }

    public String getScadenzaAbbonamento() {
        return scadenzaAbbonamento;
    }

    public Long getIngressi() {
        return ingressi;
    }
}
