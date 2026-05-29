package com.artheriom.acnhcollector.api.domain.enums;

public enum  MusicTypeEnum {
    HARD_TO_TELL("hard_to_tell"),
    BLUES("blues"),
    RELAX("relax"),
    MOODY("moody"),
    GOOD("good"),
    HIDDEN("hidden"),
    SPECIAL("special");

    public String name;

    MusicTypeEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

