package com.artheriom.acnhcollector.api.domain.dto;

import com.artheriom.acnhcollector.api.domain.enums.ArtTypeEnum;
import com.artheriom.acnhcollector.api.domain.enums.MusicTypeEnum;
import java.util.UUID;

public class APIArtDTO {
    private String id;

    private String name;

    private ArtTypeEnum type;

    private String realImage;

    private String realName;

    private String fakeImage;

    private Boolean hasFake;

    private String difference_descriptor;

    public APIArtDTO() {
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

    public ArtTypeEnum getType() {
        return type;
    }

    public void setType(ArtTypeEnum type) {
        this.type = type;
    }

    public String getRealImage() {
        return realImage;
    }

    public void setRealImage(String realImage) {
        this.realImage = realImage;
    }

    public String getFakeImage() {
        return fakeImage;
    }

    public void setFakeImage(String fakeImage) {
        this.fakeImage = fakeImage;
    }

    public Boolean getHasFake() {
        return hasFake;
    }

    public void setHasFake(Boolean hasFake) {
        this.hasFake = hasFake;
    }

    public String getDifference_descriptor() {
        return difference_descriptor;
    }

    public void setDifference_descriptor(String difference_descriptor) {
        this.difference_descriptor = difference_descriptor;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
