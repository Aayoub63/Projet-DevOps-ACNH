package com.artheriom.acnhcollector.api.service;

import com.artheriom.acnhcollector.api.domain.APIArt;
import com.artheriom.acnhcollector.api.domain.APIFossil;
import com.artheriom.acnhcollector.api.domain.APIUser;
import com.artheriom.acnhcollector.api.exceptions.NotFoundException;
import com.artheriom.acnhcollector.api.repository.APIArtRepository;
import com.artheriom.acnhcollector.api.repository.APIFossilRepository;
import com.artheriom.acnhcollector.api.repository.APIUserRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Service
public class APIUserFossilService {
    private final APIUserRepository userRepository;
    private final APIFossilRepository fossilRepository;

    public APIUserFossilService(APIUserRepository userRepository, APIFossilRepository fossilRepository){
        this.userRepository = userRepository;
        this.fossilRepository = fossilRepository;
    }

    public Collection<APIFossil> getAllFossilByUser(@NotNull String userId){
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user does not exist")).getFossilCollection();
    }

    public void addFossilToUser(@NotNull String userId, @NotNull String fossilId){
        APIFossil fossil = fossilRepository.findById(fossilId).orElseThrow(() -> new NotFoundException("fossil does not exist"));
        APIUser user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user does not exist"));

        if(!user.getFossilCollection().contains(fossil)){
            user.addFossil(fossil);
        }

        userRepository.save(user);
    }

    public void removeFossilToUser(@NotNull String userId, @NotNull String fossilId){
        APIFossil fossil = fossilRepository.findById(fossilId).orElseThrow(() -> new NotFoundException("art does not exist"));
        APIUser user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user does not exist"));

        if(user.getFossilCollection().contains(fossil)){
            user.removeFossil(fossil);

        }

        userRepository.save(user);
    }
}
