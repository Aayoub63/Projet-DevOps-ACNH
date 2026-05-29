package com.artheriom.acnhcollector.api.domain.enums;

public enum ArtTypeEnum {
    PAINTING("painting"),
    SCULPTURE("sculpture");

    public String name;

    ArtTypeEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

