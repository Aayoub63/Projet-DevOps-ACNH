package com.artheriom.acnhcollector.api.service;

import com.artheriom.acnhcollector.api.domain.APIFlower;
import com.artheriom.acnhcollector.api.domain.APIMusic;
import com.artheriom.acnhcollector.api.domain.APIUser;
import com.artheriom.acnhcollector.api.exceptions.NotFoundException;
import com.artheriom.acnhcollector.api.repository.APIFlowerRepository;
import com.artheriom.acnhcollector.api.repository.APIMusicRepository;
import com.artheriom.acnhcollector.api.repository.APIUserRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Service
public class APIUserFlowerService {
    private final APIUserRepository userRepository;
    private final APIFlowerRepository flowerRepository;

    public APIUserFlowerService(APIUserRepository userRepository, APIFlowerRepository flowerRepository){
        this.userRepository = userRepository;
        this.flowerRepository = flowerRepository;
    }

    public Collection<APIFlower> getAllFlowerByUser(@NotNull String userId){
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user does not exist")).getFlowerCollection();
    }

    public void addFlowerToUser(@NotNull String userId, @NotNull String flowerId){
        APIFlower flower = flowerRepository.findById(flowerId).orElseThrow(() -> new NotFoundException("flower does not exist"));
        APIUser user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user does not exist"));

        if(!user.getFlowerCollection().contains(flower)){
            user.addFlower(flower);
        }

        userRepository.save(user);
    }

    public void removeFlowerToUser(@NotNull String userId, @NotNull String flowerId){
        APIFlower flower = flowerRepository.findById(flowerId).orElseThrow(() -> new NotFoundException("flower does not exist"));
        APIUser user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user does not exist"));

        if(user.getFlowerCollection().contains(flower)){
            user.removeFlower(flower);
        }

        userRepository.save(user);
    }
}
