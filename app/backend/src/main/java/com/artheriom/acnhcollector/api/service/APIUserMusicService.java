package com.artheriom.acnhcollector.api.service;

import com.artheriom.acnhcollector.api.domain.APIMusic;
import com.artheriom.acnhcollector.api.domain.APIUser;
import com.artheriom.acnhcollector.api.exceptions.NotFoundException;
import com.artheriom.acnhcollector.api.repository.APIMusicRepository;
import com.artheriom.acnhcollector.api.repository.APIUserRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Service
public class APIUserMusicService {
    private final APIUserRepository userRepository;
    private final APIMusicRepository musicRepository;

    public APIUserMusicService(APIUserRepository userRepository, APIMusicRepository musicRepository){
        this.userRepository = userRepository;
        this.musicRepository = musicRepository;
    }

    public Collection<APIMusic> getAllMusicByUser(@NotNull String userId){
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user does not exist")).getMusicCollection();
    }

    public void addMusicToUser(@NotNull String userId, @NotNull String musicId){
        APIMusic music = musicRepository.findById(musicId).orElseThrow(() -> new NotFoundException("music does not exist"));
        APIUser user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user does not exist"));

        if(!user.getMusicCollection().contains(music)){
            user.addMusic(music);
        }

        userRepository.save(user);
    }

    public void removeMusicToUser(@NotNull String userId, @NotNull String musicId){
        APIMusic music = musicRepository.findById(musicId).orElseThrow(() -> new NotFoundException("music does not exist"));
        APIUser user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user does not exist"));

        if(user.getMusicCollection().contains(music)){
            user.removeMusic(music);
        }

        userRepository.save(user);
    }
}
