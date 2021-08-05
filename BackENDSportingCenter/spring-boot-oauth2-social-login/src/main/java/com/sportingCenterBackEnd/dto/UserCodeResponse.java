package com.sportingCenterBackEnd.dto;

import lombok.Value;

@Value
public class UserCodeResponse {
    private String displayName;
    private String idAbbonamento;

    public  UserCodeResponse (String displayName, String idAbbonamento) {
        this.displayName = displayName;
        this.idAbbonamento = idAbbonamento;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIdAbbonamento() {
        return idAbbonamento;
    }
}
