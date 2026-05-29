package com.artheriom.acnhcollector.api.domain.dto;

public class APITokenDTO {

    private String token;

    public APITokenDTO() {
        //Ignoré
    }

    public APITokenDTO(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
