package com.artheriom.acnhcollector.api.service;

import com.artheriom.acnhcollector.api.domain.APIFlower;
import com.artheriom.acnhcollector.api.domain.dto.APIFlowerDTO;
import com.artheriom.acnhcollector.api.exceptions.NotFoundException;
import com.artheriom.acnhcollector.api.repository.APIFlowerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public class APIFlowerService {

    private final APIFlowerRepository flowerRepository;

    public APIFlowerService(APIFlowerRepository flowerRepository){
        this.flowerRepository=flowerRepository;
    }

    public Page<APIFlower> getAll(Pageable pageable){
        return flowerRepository.findAll(pageable);
    }


    public APIFlower getById(@NotBlank String flowerId){
        return flowerRepository.findById(flowerId).orElseThrow(() -> new NotFoundException("flower " + flowerId + " does not exist."));
    }

    public APIFlower create(@NotNull APIFlowerDTO flowerDTO){
        Assert.hasText(flowerDTO.getName(), "name could not be null");

        APIFlower flower = new APIFlower();
        flower.setName(flowerDTO.getName());

        return flowerRepository.save(flower);
    }

    public void updatePatch(@NotBlank String flowerId, @NotNull APIFlowerDTO flowerDTO){
        //Check if exists
        APIFlower flower = flowerRepository.findById(flowerId).orElseThrow(() -> new NotFoundException("flower does not exist"));

        if(flowerDTO.getName()!=null && !flowerDTO.getName().equals("")){
            //Check if name does not already exist in database
            APIFlower flower1 = flowerRepository.findByName(flowerDTO.getName()).orElse(null);
            if(flower1 != null && !flower1.getId().equals(flowerId)){
                throw new IllegalArgumentException("Flower already exist with this name.");
            }
            flower.setName(flowerDTO.getName());
        }


        flowerRepository.save(flower);
    }

    @Transactional
    public void deleteById(@NotBlank String artId){
        flowerRepository.deleteById(artId);
    }

}
