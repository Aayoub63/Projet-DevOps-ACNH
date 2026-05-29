package com.artheriom.acnhcollector.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@Entity
public class APIUser {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    private String username;

    @NotBlank
    @JsonIgnore
    private String password;

    @NotBlank
    private String email;

    @JsonIgnore
    @ManyToMany
    Collection<APIMusic> musicCollection = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    Collection<APIArt> artCollection = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    Collection<APIFossil> fossilCollection = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    Collection<APIFlower> flowerCollection = new HashSet<>();

    @JsonIgnore
    private boolean isAdmin = false;

    private String switchCode;


    public APIUser() {
        //Ignored
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
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

    public Collection<APIMusic> getMusicCollection() {
        return musicCollection;
    }

    public void setMusicCollection(Collection<APIMusic> musicCollection) {
        this.musicCollection = musicCollection;
    }

    public void addMusic(APIMusic music){
        this.musicCollection.add(music);
    }

    public void removeMusic(APIMusic music){
        this.musicCollection.remove(music);
    }

    public Collection<APIArt> getArtCollection() {
        return artCollection;
    }

    public void setArtCollection(Collection<APIArt> artCollection) {
        this.artCollection = artCollection;
    }

    public void addArt(APIArt art){
        this.artCollection.add(art);
    }

    public void removeArt(APIArt art){
        this.artCollection.remove(art);
    }

    public void addFossil(APIFossil fossil) {
        this.fossilCollection.add(fossil);
    }

    public void removeFossil(APIFossil fossil){
        this.fossilCollection.remove(fossil);
    }

    public Collection<APIFossil> getFossilCollection() {
        return fossilCollection;
    }

    public void setFossilCollection(Collection<APIFossil> fossilCollection) {
        this.fossilCollection = fossilCollection;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getSwitchCode() {
        return switchCode;
    }

    public void setSwitchCode(String switchCode) {
        this.switchCode = switchCode;
    }

    public Collection<APIFlower> getFlowerCollection() {
        return flowerCollection;
    }

    public void setFlowerCollection(Collection<APIFlower> flowerCollection) {
        this.flowerCollection = flowerCollection;
    }

    public void addFlower(APIFlower flower){
        this.flowerCollection.add(flower);
    }

    public void removeFlower(APIFlower flower){
        this.flowerCollection.remove(flower);
    }
}
