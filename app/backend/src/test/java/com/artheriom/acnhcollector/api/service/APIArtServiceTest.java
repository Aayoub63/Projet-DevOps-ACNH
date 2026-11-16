package com.artheriom.acnhcollector.api.service;

import com.artheriom.acnhcollector.api.domain.APIArt;
import com.artheriom.acnhcollector.api.domain.dto.APIArtDTO;
import com.artheriom.acnhcollector.api.domain.enums.ArtTypeEnum;
import com.artheriom.acnhcollector.api.exceptions.NotFoundException;
import com.artheriom.acnhcollector.api.repository.APIArtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Tests unitaires du service : le repository est simulé avec Mockito,
// pas besoin de base de données.
class APIArtServiceTest {

    private APIArtRepository artRepository;
    private APIArtService artService;

    @BeforeEach
    void setUp() {
        artRepository = mock(APIArtRepository.class);
        artService = new APIArtService(artRepository);
    }

    @Test
    void getById_retourneLaToileSiElleExiste() {
        APIArt art = new APIArt();
        art.setName("Toile tragique");
        when(artRepository.findById("id-1")).thenReturn(Optional.of(art));

        assertEquals("Toile tragique", artService.getById("id-1").getName());
    }

    @Test
    void getById_leveNotFoundSiLaToileNexistePas() {
        when(artRepository.findById("inconnu")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> artService.getById("inconnu"));
    }

    @Test
    void create_refuseUnNomDejaUtilise() {
        when(artRepository.findByName("Toile tragique")).thenReturn(Optional.of(new APIArt()));

        APIArtDTO dto = dto("Toile tragique");

        assertThrows(IllegalArgumentException.class, () -> artService.create(dto));
        verify(artRepository, never()).save(any());
    }

    @Test
    void create_refuseUnNomVide() {
        assertThrows(IllegalArgumentException.class, () -> artService.create(dto("")));
    }

    @Test
    void create_enregistreUneNouvelleToile() {
        when(artRepository.findByName("Nouvelle toile")).thenReturn(Optional.empty());
        when(artRepository.save(any(APIArt.class))).thenAnswer(invocation -> invocation.getArgument(0));

        APIArt cree = artService.create(dto("Nouvelle toile"));

        assertEquals("Nouvelle toile", cree.getName());
        verify(artRepository).save(any(APIArt.class));
    }

    private APIArtDTO dto(String name) {
        APIArtDTO dto = new APIArtDTO();
        dto.setName(name);
        dto.setType(ArtTypeEnum.PAINTING);
        dto.setHasFake(false);
        return dto;
    }
}
