package com.artheriom.acnhcollector.api.service;

import com.artheriom.acnhcollector.api.domain.APIUser;
import com.artheriom.acnhcollector.api.domain.dto.APIUserDTO;
import com.artheriom.acnhcollector.api.exceptions.NotFoundException;
import com.artheriom.acnhcollector.api.repository.APIUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public class APIUserService {

    private final APIUserRepository userRepository;

    public APIUserService(APIUserRepository userRepository){
        this.userRepository=userRepository;
    }

    public Page<APIUser> getAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Page<APIUser> getAllByName(@NotBlank String username, Pageable pageable){
        return userRepository.findAllByUsername(username, pageable);
    }

    public APIUser getById(@NotBlank String userId){
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user " + userId + " does not exist."));
    }

    public APIUser create(@NotNull APIUserDTO userDTO){
        Assert.hasText(userDTO.getUsername(), "username cannot be null or empty");
        Assert.hasText(userDTO.getPassword(), "password cannot be null or empty");
        Assert.hasText(userDTO.getEmail(), "email cannot be null or empty");

        //Check if username or email already exists
        APIUser user = userRepository.findByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail()).orElse(null);
        if(user != null){
            throw new IllegalArgumentException("An user already exists with the same username or email.");
        }

        //Create new user.
        user = new APIUser();
        user.setUsername(userDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder(13).encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());

        if(userDTO.getSwitchCode() != null){
            validateSwitchCode(userDTO.getSwitchCode());
            user.setSwitchCode(userDTO.getSwitchCode());
        }

        return userRepository.save(user);
    }

    public void updatePatch(@NotBlank String userId, @NotNull APIUserDTO userDTO){
        //Get user exist.
        APIUser user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User " + userId + "does not exist"));

        if(userDTO.getEmail()!=null && !userDTO.getEmail().equals("")) {
            APIUser checkMail = userRepository.findByEmail(userDTO.getEmail()).orElse(null);
            if(checkMail!=null && !checkMail.getId().equals(userId)){
                throw new IllegalArgumentException("An user with the same email already exist");
            }
            user.setEmail(userDTO.getEmail());
        }

        if(userDTO.getUsername()!=null && !userDTO.getUsername().equals("")){
            APIUser checkUsername = userRepository.findByUsername(userDTO.getUsername()).orElse(null);
            if(checkUsername!=null && !checkUsername.getId().equals(userId)){
                throw new IllegalArgumentException("An user with the same username already exist");
            }
            user.setUsername(userDTO.getUsername());
        }

        if(userDTO.getSwitchCode() != null){
            validateSwitchCode(userDTO.getSwitchCode());
            user.setSwitchCode(userDTO.getSwitchCode());
        }

        if(userDTO.getPassword()!=null && !userDTO.getPassword().equals("")){
            user.setPassword(new BCryptPasswordEncoder(13).encode(userDTO.getPassword()));
        }

        userRepository.save(user);
    }

    @Transactional
    public void deleteById(@NotBlank String userId){
        userRepository.deleteById(userId);
    }


    //----- Méthodes privées

    private void validateSwitchCode(String code){
        if(!code.equals("") && !code.matches("^SW-[0-9]{4}-[0-9]{4}-[0-9]{4}$")){
            throw new IllegalArgumentException("Switch Code is not valid !");
        }
    }

}

