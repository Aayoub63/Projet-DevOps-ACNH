package com.artheriom.acnhcollector.api.controller;

import com.artheriom.acnhcollector.api.domain.APIArt;
import com.artheriom.acnhcollector.api.domain.dto.APIArtDTO;
import com.artheriom.acnhcollector.api.exceptions.ForbiddenException;
import com.artheriom.acnhcollector.api.security.APIAuthenticationService;
import com.artheriom.acnhcollector.api.service.APIArtService;
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
@RequestMapping("/arts")
public class APIArtController {
    private final APIArtService artService;
    private final APIAuthenticationService authenticationService;

    public APIArtController(APIArtService artService, APIAuthenticationService authenticationService){
        this.artService = artService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("")
    public ResponseEntity<Page<APIArt>> getAll(Pageable pageable){
        Page<APIArt> apiArts =  artService.getAll(pageable);
        return (apiArts.getSize()==0)?ResponseEntity.noContent().build():ResponseEntity.ok(apiArts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIArt> getById(@PathVariable("id") String id){
        return ResponseEntity.ok(artService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<Void> create(@NotNull @RequestBody @Validated APIArtDTO artDTO, UriComponentsBuilder ucb, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }
        return ResponseEntity.created(ucb.path("/arts/" + artService.create(artDTO).getId()).build().toUri()).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> create(@PathVariable("id") String id, @NotNull @RequestBody @Validated APIArtDTO artDTO, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }

        artService.updatePatch(id, artDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }

        artService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}