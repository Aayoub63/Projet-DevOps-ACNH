package com.artheriom.acnhcollector.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Tests d'intégration de l'API : l'application démarre vraiment,
// avec la base H2 en mémoire définie dans src/test/resources.
@SpringBootTest
@AutoConfigureMockMvc
class APIArtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listeDesToiles_repond200() throws Exception {
        mockMvc.perform(get("/arts"))
                .andExpect(status().isOk());
    }

    @Test
    void toileInexistante_repond404() throws Exception {
        mockMvc.perform(get("/arts/id-qui-nexiste-pas"))
                .andExpect(status().isNotFound());
    }

    @Test
    void creationSansToken_repond401() throws Exception {
        String body = "{\"name\":\"Test\",\"type\":\"PAINTING\",\"hasFake\":false}";

        mockMvc.perform(post("/arts").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginAvecMauvaisMotDePasse_repond401() throws Exception {
        String body = "{\"username\":\"testUsername\",\"password\":\"mauvais\"}";

        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }
}
