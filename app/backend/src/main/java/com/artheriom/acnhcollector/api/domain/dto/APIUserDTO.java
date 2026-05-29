package com.artheriom.acnhcollector.api.domain.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class APIUserDTO {

    private String id;

    private String username;

    private String password;

    private String email;

    Collection<APIMusicDTO> musicCollection = new HashSet<>();

    Collection<APIArtDTO> artCollection = new HashSet<>();

    private String switchCode;


    public APIUserDTO() {
        //ignoré
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<APIMusicDTO> getMusicCollection() {
        return musicCollection;
    }

    public void setMusicCollection(Collection<APIMusicDTO> musicCollection) {
        this.musicCollection = musicCollection;
    }

    public Collection<APIArtDTO> getArtCollection() {
        return artCollection;
    }

    public void setArtCollection(Collection<APIArtDTO> artCollection) {
        this.artCollection = artCollection;
    }

    public String getSwitchCode() {
        return switchCode;
    }

    public void setSwitchCode(String switchCode) {
        this.switchCode = switchCode;
    }
}
