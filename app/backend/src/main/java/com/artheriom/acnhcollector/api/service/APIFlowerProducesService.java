package com.artheriom.acnhcollector.api.service;

import com.artheriom.acnhcollector.api.domain.APIFlower;
import com.artheriom.acnhcollector.api.domain.APIFlowerProduces;
import com.artheriom.acnhcollector.api.domain.dto.APIFlowerDTO;
import com.artheriom.acnhcollector.api.domain.dto.APIFlowerProducesDTO;
import com.artheriom.acnhcollector.api.exceptions.NotFoundException;
import com.artheriom.acnhcollector.api.repository.APIFlowerProducesRepository;
import com.artheriom.acnhcollector.api.repository.APIFlowerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public class APIFlowerProducesService {

    private final APIFlowerProducesRepository repository;
    private final APIFlowerRepository flowerRepository;

    public APIFlowerProducesService(APIFlowerProducesRepository repository, APIFlowerRepository flowerRepository){
        this.repository=repository;
        this.flowerRepository=flowerRepository;
    }

    public Page<APIFlowerProduces> getAll(Pageable pageable){
        return repository.findAll(pageable);
    }


    public APIFlowerProduces getById(@NotBlank String flowerId){
        return repository.findById(flowerId).orElseThrow(() -> new NotFoundException("flowerProduces " + flowerId + " does not exist."));
    }

    public APIFlowerProduces create(@NotNull APIFlowerProducesDTO dto){
        Assert.hasText(dto.getSourceA().getId(), "idA could not be null");
        Assert.hasText(dto.getSourceB().getId(), "idB could not be null");
        Assert.hasText(dto.getProduces().getId(), "idP could not be null");

        APIFlowerProduces produces = new APIFlowerProduces();
        produces.setSourceA(flowerRepository.findById(dto.getSourceA().getId()).orElseThrow(() -> new NotFoundException("flower A")));
        produces.setSourceB(flowerRepository.findById(dto.getSourceB().getId()).orElseThrow(() -> new NotFoundException("flower B")));
        produces.setProduces(flowerRepository.findById(dto.getProduces().getId()).orElseThrow(() -> new NotFoundException("flower P")));

        return repository.save(produces);
    }


    @Transactional
    public void deleteById(@NotBlank String artId){
        repository.deleteById(artId);
    }

}
