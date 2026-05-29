package com.artheriom.acnhcollector.api.controller;

import com.artheriom.acnhcollector.api.domain.APIFlower;
import com.artheriom.acnhcollector.api.domain.dto.APIFlowerDTO;
import com.artheriom.acnhcollector.api.exceptions.ForbiddenException;
import com.artheriom.acnhcollector.api.security.APIAuthenticationService;
import com.artheriom.acnhcollector.api.service.APIFlowerService;
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
@RequestMapping("/flowers")
public class APIFlowerController {
    private final APIFlowerService flowerService;
    private final APIAuthenticationService authenticationService;

    public APIFlowerController(APIFlowerService flowerService, APIAuthenticationService authenticationService){
        this.flowerService = flowerService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("")
    public ResponseEntity<Page<APIFlower>> getAll(Pageable pageable){
        Page<APIFlower> flowers =  flowerService.getAll(pageable);
        return (flowers.getSize()==0)?ResponseEntity.noContent().build():ResponseEntity.ok(flowers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIFlower> getById(@PathVariable("id") String id){
        return ResponseEntity.ok(flowerService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<Void> create(@NotNull @RequestBody @Validated APIFlowerDTO dto, UriComponentsBuilder ucb, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }
        return ResponseEntity.created(ucb.path("/flowers/" + flowerService.create(dto).getId()).build().toUri()).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> create(@PathVariable("id") String id, @NotNull @RequestBody @Validated APIFlowerDTO dto, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }

        flowerService.updatePatch(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }

        flowerService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}