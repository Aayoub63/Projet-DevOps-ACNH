package com.artheriom.acnhcollector.api.domain;

import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
public class APIFossil {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    private String dinosaurName;

    private String dinosaurPart;

    private String image = "/static/fossils/"+RandomStringUtils.randomAlphanumeric(32)+".png";


    public APIFossil() {
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

    public void setDinosaurName(String dinosaur_name) {
        this.dinosaurName = dinosaur_name;
    }

    public String getDinosaurPart() {
        return dinosaurPart;
    }

    public void setDinosaurPart(String dinosaur_part) {
        this.dinosaurPart = dinosaur_part;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
