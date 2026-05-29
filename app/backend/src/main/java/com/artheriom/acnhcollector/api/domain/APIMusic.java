package com.artheriom.acnhcollector.api.domain;

import com.artheriom.acnhcollector.api.domain.enums.MusicTypeEnum;
import org.apache.commons.lang3.RandomStringUtils;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class APIMusic {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    private String name;

    @NotNull
    private MusicTypeEnum type;

    @NotBlank
    private String image = "/static/musics/"+RandomStringUtils.randomAlphanumeric(32)+".png";


    public APIMusic() {
        //Ignored
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

    public MusicTypeEnum getType() {
        return type;
    }

    public void setType(MusicTypeEnum type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
