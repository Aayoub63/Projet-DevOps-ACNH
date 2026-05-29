package com.artheriom.acnhcollector.api.controller;

import com.artheriom.acnhcollector.api.domain.APIMusic;
import com.artheriom.acnhcollector.api.domain.dto.APIMusicDTO;
import com.artheriom.acnhcollector.api.service.APIMusicService;
import com.artheriom.acnhcollector.api.exceptions.ForbiddenException;
import com.artheriom.acnhcollector.api.security.APIAuthenticationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/musics")
public class APIMusicController {
    private final APIMusicService musicService;
    private final APIAuthenticationService authenticationService;

    public APIMusicController(APIMusicService musicService, APIAuthenticationService authenticationService){
        this.musicService = musicService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("")
    public ResponseEntity<Page<APIMusic>> getAll(Pageable pageable){
        Page<APIMusic> apimusics =  musicService.getAll(pageable);
        return (apimusics.getSize()==0)?ResponseEntity.noContent().build():ResponseEntity.ok(apimusics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIMusic> getById(@PathVariable("id") String id){
        return ResponseEntity.ok(musicService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<Void> create(@NotNull @RequestBody @Validated APIMusicDTO musicDTO, UriComponentsBuilder ucb, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }
        return ResponseEntity.created(ucb.path("/musics/" + musicService.create(musicDTO).getId()).build().toUri()).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> create(@PathVariable("id") String id, @NotNull @RequestBody @Validated APIMusicDTO musicDTO, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }

        musicService.updatePatch(id, musicDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }

        musicService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}