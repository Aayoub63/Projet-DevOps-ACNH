package com.artheriom.acnhcollector.api.domain.dto;

import com.artheriom.acnhcollector.api.domain.APIFlower;

public class APIFlowerProducesDTO {

    private String id;

    private APIFlowerDTO sourceA;

    private APIFlowerDTO sourceB;

    private APIFlowerDTO produces;

    public APIFlowerProducesDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public APIFlowerDTO getSourceA() {
        return sourceA;
    }

    public void setSourceA(APIFlowerDTO sourceA) {
        this.sourceA = sourceA;
    }

    public APIFlowerDTO getSourceB() {
        return sourceB;
    }

    public void setSourceB(APIFlowerDTO sourceB) {
        this.sourceB = sourceB;
    }

    public APIFlowerDTO getProduces() {
        return produces;
    }

    public void setProduces(APIFlowerDTO produces) {
        this.produces = produces;
    }
}
