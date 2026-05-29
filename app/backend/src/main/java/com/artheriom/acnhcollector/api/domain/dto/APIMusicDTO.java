package com.artheriom.acnhcollector.api.domain.dto;

import com.artheriom.acnhcollector.api.domain.enums.MusicTypeEnum;
import java.util.UUID;

public class APIMusicDTO {

    private String id;

    private String name;

    private MusicTypeEnum type;

    private String image;

    public APIMusicDTO() {
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
