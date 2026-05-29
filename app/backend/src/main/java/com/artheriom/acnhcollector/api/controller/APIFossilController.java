package com.artheriom.acnhcollector.api.controller;

import com.artheriom.acnhcollector.api.domain.APIFossil;
import com.artheriom.acnhcollector.api.domain.dto.APIFossilDTO;
import com.artheriom.acnhcollector.api.exceptions.ForbiddenException;
import com.artheriom.acnhcollector.api.security.APIAuthenticationService;
import com.artheriom.acnhcollector.api.service.APIFossilService;
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
@RequestMapping("/fossils")
public class APIFossilController {
    private final APIFossilService fossilService;
    private final APIAuthenticationService authenticationService;

    public APIFossilController(APIFossilService fossilService, APIAuthenticationService authenticationService){
        this.fossilService = fossilService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("")
    public ResponseEntity<Page<APIFossil>> getAll(Pageable pageable){
        Page<APIFossil> apifossils =  fossilService.getAll(pageable);
        return (apifossils.getSize()==0)?ResponseEntity.noContent().build():ResponseEntity.ok(apifossils);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIFossil> getById(@PathVariable("id") String id){
        return ResponseEntity.ok(fossilService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<Void> create(@NotNull @RequestBody @Validated APIFossilDTO fossilDTO, UriComponentsBuilder ucb, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }
        return ResponseEntity.created(ucb.path("/fossils/" + fossilService.create(fossilDTO).getId()).build().toUri()).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> create(@PathVariable("id") String id, @NotNull @RequestBody @Validated APIFossilDTO fossilDTO, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }

        fossilService.updatePatch(id, fossilDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id, HttpServletRequest request){
        //Check if administrator.
        if(!authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()){
            throw new ForbiddenException("You are not administrator");
        }

        fossilService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}