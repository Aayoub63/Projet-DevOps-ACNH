package com.artheriom.acnhcollector.api.controller;

import com.artheriom.acnhcollector.api.domain.APIFlowerProduces;
import com.artheriom.acnhcollector.api.domain.dto.APIFlowerProducesDTO;
import com.artheriom.acnhcollector.api.exceptions.ForbiddenException;
import com.artheriom.acnhcollector.api.security.APIAuthenticationService;
import com.artheriom.acnhcollector.api.service.APIFlowerProducesService;
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
@RequestMapping("/flowersProduces")
public class APIFlowerProducesController {
    private final APIFlowerProducesService producesService;
    private final APIAuthenticationService authenticationService;

    public APIFlowerProducesController(APIFlowerProducesService producesService, APIAuthenticationService authenticationService){
        this.producesService = producesService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("")
    public ResponseEntity<Page<APIFlowerProduces>> getAll(Pageable pageable){
        Page<APIFlowerProduces> flowers =  producesService.getAll(pageable);
        return (flowers.getSize()==0)?ResponseEntity.noContent().build():ResponseEntity.ok(flowers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIFlowerProduces> getById(@PathVariable("id") String id){
        return ResponseEntity.ok(producesService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<Void> create(@NotNull @RequestBody @Validated APIFlowerProducesDTO dto, UriComponentsBuilder ucb, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }
        return ResponseEntity.created(ucb.path("/flowersProduces/" + producesService.create(dto).getId()).build().toUri()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }

        producesService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}