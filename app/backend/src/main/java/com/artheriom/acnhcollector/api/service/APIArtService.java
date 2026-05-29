package com.artheriom.acnhcollector.api.service;

import com.artheriom.acnhcollector.api.domain.APIArt;
import com.artheriom.acnhcollector.api.domain.dto.APIArtDTO;
import com.artheriom.acnhcollector.api.exceptions.NotFoundException;
import com.artheriom.acnhcollector.api.repository.APIArtRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public class APIArtService {

    private final APIArtRepository artRepository;

    public APIArtService(APIArtRepository artRepository){
        this.artRepository=artRepository;
    }

    public Page<APIArt> getAll(Pageable pageable){
        return artRepository.findAll(pageable);
    }


    public APIArt getById(@NotBlank String artId){
        return artRepository.findById(artId).orElseThrow(() -> new NotFoundException("art " + artId + " does not exist."));
    }

    public APIArt create(@NotNull APIArtDTO artDTO){
        Assert.hasText(artDTO.getName(), "name could not be null");
        Assert.notNull(artDTO.getType(), "type could not be null");
        Assert.notNull(artDTO.getHasFake(), "hasFake could not be null");

        //Check if name already exist
        APIArt art = artRepository.findByName(artDTO.getName()).orElse(null);

        if(art!=null){
            throw new IllegalArgumentException("art name already exist.");
        }

        art = new APIArt();
        art.setName(artDTO.getName());
        art.setDifference_descriptor(artDTO.getDifference_descriptor());
        art.setType(artDTO.getType());
        art.setHasFake(artDTO.getHasFake());
        art.setRealName(artDTO.getRealName());

        return artRepository.save(art);
    }

    public void updatePatch(@NotBlank String artId, @NotNull APIArtDTO artDTO){
        //Check if exists
        APIArt art = artRepository.findById(artId).orElseThrow(() -> new NotFoundException("art does not exist"));

        if(artDTO.getName()!=null && !artDTO.getName().equals("")){
            //Check if name does not already exist in database
            APIArt art1 = artRepository.findByName(artDTO.getName()).orElse(null);
            if(art1 != null && !art1.getId().equals(artId)){
                throw new IllegalArgumentException("Art already exist with this name.");
            }
            art.setName(artDTO.getName());
        }

        if(artDTO.getDifference_descriptor()!=null && !artDTO.getDifference_descriptor().equals("")){
            art.setDifference_descriptor(artDTO.getDifference_descriptor());
        }

        if (artDTO.getType() != null) {
            art.setType(artDTO.getType());
        }

        if (artDTO.getHasFake()!=null){
           art.setHasFake(artDTO.getHasFake());
        }

        if(artDTO.getRealName()!=null){
            art.setRealName(artDTO.getRealName());
        }

        artRepository.save(art);
    }

    @Transactional
    public void deleteById(@NotBlank String artId){
        artRepository.deleteById(artId);
    }

}
