package com.artheriom.acnhcollector.api.domain;

import com.artheriom.acnhcollector.api.domain.enums.ArtTypeEnum;
import com.artheriom.acnhcollector.api.domain.enums.MusicTypeEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class APIArt {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    private String name;

    private String realName;

    @NotNull
    private ArtTypeEnum type;

    @NotBlank
    private String realImage;

    private String fakeImage;

    private Boolean hasFake;

    @Length(max = 800)
    private String difference_descriptor;


    public APIArt() {
        String url = RandomStringUtils.randomAlphanumeric(32);
        realImage = "/static/arts/"+url+"-real.png";
        fakeImage = "/static/arts/"+url+"-fake.png";
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
