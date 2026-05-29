package com.artheriom.acnhcollector.api.domain.dto;

public class APIFossilDTO {

    private String id;

    private String dinosaurName;

    private String dinosaurPart;

    private String image;


    public APIFossilDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDinosaurName() {
        return dinosaurName;
    }

    public void setDinosaurName(String dinosaurName) {
        this.dinosaurName = dinosaurName;
    }

    public String getDinosaurPart() {
        return dinosaurPart;
    }

    public void setDinosaurPart(String dinosaurPart) {
        this.dinosaurPart = dinosaurPart;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
