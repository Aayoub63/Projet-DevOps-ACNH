package com.artheriom.acnhcollector.api.security;

import com.artheriom.acnhcollector.api.domain.dto.APIAuthDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class APIAuthenticationController {

    APIAuthenticationService apiAuthenticationService;

    APIAuthenticationController(APIAuthenticationService apiAuthenticationService){
        this.apiAuthenticationService = apiAuthenticationService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Void> login(@NotNull @Validated @RequestBody APIAuthDTO apiAuthDTO){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", apiAuthenticationService.login(apiAuthDTO));

        return ResponseEntity.ok().headers(httpHeaders).build();
    }

}
