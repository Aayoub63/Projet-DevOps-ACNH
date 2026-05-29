package com.artheriom.acnhcollector.api.service;

import com.artheriom.acnhcollector.api.domain.APIFossil;
import com.artheriom.acnhcollector.api.domain.dto.APIFossilDTO;
import com.artheriom.acnhcollector.api.exceptions.NotFoundException;
import com.artheriom.acnhcollector.api.repository.APIFossilRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public class APIFossilService {

    private final APIFossilRepository fossilRepository;

    public APIFossilService(APIFossilRepository fossilRepository){
        this.fossilRepository=fossilRepository;
    }

    public Page<APIFossil> getAll(Pageable pageable){
        return fossilRepository.findAll(pageable);
    }


    public APIFossil getById(@NotBlank String fossilId){
        return fossilRepository.findById(fossilId).orElseThrow(() -> new NotFoundException("fossil " + fossilId + " does not exist."));
    }

    public APIFossil create(@NotNull APIFossilDTO fossilDTO){
        Assert.hasText(fossilDTO.getDinosaurName(), "fossilName could not be empty");

        APIFossil fossil = new APIFossil();
        fossil.setDinosaurName(fossilDTO.getDinosaurName());
        fossil.setDinosaurPart(fossilDTO.getDinosaurPart());

        return fossilRepository.save(fossil);
    }

    public void updatePatch(@NotBlank String fossilId, @NotNull APIFossilDTO fossilDTO){
        //Check if fossil exist
        APIFossil fossil = fossilRepository.findById(fossilId).orElseThrow(() -> new NotFoundException("fossil does not exist"));

        if(fossilDTO.getDinosaurName() != null && !fossilDTO.getDinosaurName().equals("")){
            fossil.setDinosaurName(fossilDTO.getDinosaurName());
        }

        if(fossilDTO.getDinosaurPart() != null && !fossilDTO.getDinosaurPart().equals("")){
            fossil.setDinosaurPart(fossilDTO.getDinosaurPart());
        }

        fossilRepository.save(fossil);
    }

    @Transactional
    public void deleteById(@NotBlank String fossilId){
        fossilRepository.deleteById(fossilId);
    }

}
