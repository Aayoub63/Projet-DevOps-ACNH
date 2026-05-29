package com.artheriom.acnhcollector.api.domain;

import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
public class APIFlower {
    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    private String name;

    @NotBlank
    private String image = "/static/flowers/"+ RandomStringUtils.randomAlphanumeric(32)+".png";

    public APIFlower() {
        //Ignoré
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
