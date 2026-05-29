package com.artheriom.acnhcollector.api.domain.dto;

public class APIErrorDTO {

    private int httpCode;
    private String details;

    public APIErrorDTO(int httpCode, String details) {
        this.httpCode = httpCode;
        this.details = details;
    }

    public APIErrorDTO() {
        //Ignoré
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
