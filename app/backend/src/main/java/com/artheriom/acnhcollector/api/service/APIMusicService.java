package com.artheriom.acnhcollector.api.service;

import com.artheriom.acnhcollector.api.domain.APIMusic;
import com.artheriom.acnhcollector.api.domain.dto.APIMusicDTO;
import com.artheriom.acnhcollector.api.exceptions.NotFoundException;
import com.artheriom.acnhcollector.api.repository.APIMusicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public class APIMusicService {

    private final APIMusicRepository musicRepository;

    public APIMusicService(APIMusicRepository musicRepository){
        this.musicRepository=musicRepository;
    }

    public Page<APIMusic> getAll(Pageable pageable){
        return musicRepository.findAll(pageable);
    }


    public APIMusic getById(@NotBlank String musicId){
        return musicRepository.findById(musicId).orElseThrow(() -> new NotFoundException("music " + musicId + " does not exist."));
    }

    public APIMusic create(@NotNull APIMusicDTO musicDTO){
        Assert.hasText(musicDTO.getName(), "musicName could not be empty");
        Assert.notNull(musicDTO.getType(), "musicType could not be empty");

        //Check if music with same name already exist
        APIMusic check = musicRepository.findByName(musicDTO.getName()).orElse(null);
        if(check!=null){
            throw new IllegalArgumentException("music with the same name already exist");
        }

        APIMusic music = new APIMusic();
        music.setName(musicDTO.getName());
        music.setType(musicDTO.getType());

        return musicRepository.save(music);
    }

    public void updatePatch(@NotBlank String musicId, @NotNull APIMusicDTO musicDTO){
        //Check if music exist
        APIMusic music = musicRepository.findById(musicId).orElseThrow(() -> new NotFoundException("music does not exist"));

        if(musicDTO.getName() != null && !musicDTO.getName().equals("")){
            music.setName(musicDTO.getName());
        }

        if(musicDTO.getType() != null){
            music.setType(musicDTO.getType());
        }

        musicRepository.save(music);
    }

    @Transactional
    public void deleteById(@NotBlank String musicId){
        musicRepository.deleteById(musicId);
    }

}
