package com.artheriom.acnhcollector.api.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class APIFlowerProduces {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull
    @ManyToOne
    private APIFlower sourceA;

    @NotNull
    @ManyToOne
    private APIFlower sourceB;

    @NotNull
    @ManyToOne
    private APIFlower produces;

    public APIFlowerProduces() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public APIFlower getSourceA() {
        return sourceA;
    }

    public void setSourceA(APIFlower sourceA) {
        this.sourceA = sourceA;
    }

    public APIFlower getSourceB() {
        return sourceB;
    }

    public void setSourceB(APIFlower sourceB) {
        this.sourceB = sourceB;
    }

    public APIFlower getProduces() {
        return produces;
    }

    public void setProduces(APIFlower produces) {
        this.produces = produces;
    }
}
