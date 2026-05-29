package com.artheriom.acnhcollector.api.service;

import com.artheriom.acnhcollector.api.domain.APIArt;
import com.artheriom.acnhcollector.api.domain.APIUser;
import com.artheriom.acnhcollector.api.exceptions.NotFoundException;
import com.artheriom.acnhcollector.api.repository.APIArtRepository;
import com.artheriom.acnhcollector.api.repository.APIUserRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Service
public class APIUserArtService {
    private final APIUserRepository userRepository;
    private final APIArtRepository artRepository;

    public APIUserArtService(APIUserRepository userRepository, APIArtRepository artRepository){
        this.userRepository = userRepository;
        this.artRepository = artRepository;
    }

    public Collection<APIArt> getAllArtByUser(@NotNull String userId){
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user does not exist")).getArtCollection();
    }

    public void addArtToUser(@NotNull String userId, @NotNull String artId){
        APIArt art = artRepository.findById(artId).orElseThrow(() -> new NotFoundException("art does not exist"));
        APIUser user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user does not exist"));

        if(!user.getArtCollection().contains(art)){
            user.addArt(art);
        }

        userRepository.save(user);
    }

    public void removeArtToUser(@NotNull String userId, @NotNull String artId){
        APIArt art = artRepository.findById(artId).orElseThrow(() -> new NotFoundException("art does not exist"));
        APIUser user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user does not exist"));

        if(user.getArtCollection().contains(art)){
            user.removeArt(art);

        }

        userRepository.save(user);
    }
}
