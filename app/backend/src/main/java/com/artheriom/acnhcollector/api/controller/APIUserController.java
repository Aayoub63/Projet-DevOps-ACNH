package com.artheriom.acnhcollector.api.controller;

import com.artheriom.acnhcollector.api.domain.*;
import com.artheriom.acnhcollector.api.domain.dto.APIUserDTO;
import com.artheriom.acnhcollector.api.exceptions.ForbiddenException;
import com.artheriom.acnhcollector.api.security.APIAuthenticationService;
import com.artheriom.acnhcollector.api.service.*;
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
import java.util.Collection;

@Controller
@RequestMapping("/users")
public class APIUserController {
    private final APIUserService userService;
    private final APIAuthenticationService authenticationService;
    private final APIUserFossilService userFossilService;
    private final APIUserArtService userArtService;
    private final APIUserFlowerService userFlowerService;
    private final APIUserMusicService userMusicService;

    public APIUserController(APIUserFlowerService userFlowerService, APIUserService userService, APIUserFossilService userFossilService, APIAuthenticationService authenticationService, APIUserArtService userArtService, APIUserMusicService userMusicService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.userArtService = userArtService;
        this.userMusicService = userMusicService;
        this.userFossilService = userFossilService;
        this.userFlowerService = userFlowerService;
    }

    @GetMapping("")
    public ResponseEntity<Page<APIUser>> getAll(Pageable pageable, HttpServletRequest request, @RequestParam(value = "search", required = false) String search) {
        //Check if logged and administrator
        Page<APIUser> users;
        if (search != null && !search.equals("")) {
            users = userService.getAllByName(search, pageable);
        } else {
            users = userService.getAll(pageable);
        }


        if (request.getHeader(HttpHeaders.AUTHORIZATION) == null || !authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin()) {
            users.forEach(e -> e.setEmail(null));
        }

        return (users.getSize() == 0) ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIUser> getById(@PathVariable("id") String id, HttpServletRequest request) {
        if (id.equals("me")) {
            id = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        APIUser user = userService.getById(id);

        if (!authenticationService.isCurrentUser(id, request)) {
            user.setEmail(null);
        }

        return ResponseEntity.ok(userService.getById(id));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody @NotNull APIUserDTO userDTO, HttpServletRequest request) {
        if (id.equals("me")) {
            id = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        if (!authenticationService.isCurrentUser(id, request)) {
            throw new ForbiddenException("You are not allowed to edit this user.");
        }

        userService.updatePatch(id, userDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id, HttpServletRequest request) {
        if (id.equals("me")) {
            id = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        if (!authenticationService.isCurrentUser(id, request)) {
            throw new ForbiddenException("You are not allowed to edit this user.");
        }

        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("")
    public ResponseEntity<Void> create(@NotNull @Validated @RequestBody APIUserDTO userDTO, UriComponentsBuilder ucb) {
        return ResponseEntity.created(ucb.path("/arts/" + userService.create(userDTO).getId()).build().toUri()).build();
    }


    // Art subpart
    @GetMapping("/{id}/arts")
    public ResponseEntity<Collection<APIArt>> getUserArts(@PathVariable("id") String id, HttpServletRequest request) {
        if (id.equals("me")) {
            id = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        return ResponseEntity.ok(userArtService.getAllArtByUser(id));
    }

    @PutMapping("/{id}/arts/{artId}")
    public ResponseEntity<Void> addUserArt(@PathVariable("id") String userId, @PathVariable("artId") String artId, HttpServletRequest request) {
        if (userId.equals("me")) {
            userId = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        if (!authenticationService.isCurrentUser(userId, request)) {
            throw new ForbiddenException("You are not allowed to edit this user.");
        }

        userArtService.addArtToUser(userId, artId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/arts/{artId}")
    public ResponseEntity<Void> deleteUserArt(@PathVariable("id") String userId, @PathVariable("artId") String artId, HttpServletRequest request) {
        if (userId.equals("me")) {
            userId = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        if (!authenticationService.isCurrentUser(userId, request)) {
            throw new ForbiddenException("You are not allowed to edit this user.");
        }

        userArtService.removeArtToUser(userId, artId);
        return ResponseEntity.ok().build();
    }

    // Music subpart
    @GetMapping("/{id}/musics")
    public ResponseEntity<Collection<APIMusic>> getUserMusics(@PathVariable("id") String id, HttpServletRequest request) {
        if (id.equals("me")) {
            id = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        return ResponseEntity.ok(userMusicService.getAllMusicByUser(id));
    }

    @PutMapping("/{id}/musics/{musicId}")
    public ResponseEntity<Void> addUserMusic(@PathVariable("id") String userId, @PathVariable("musicId") String musicId, HttpServletRequest request) {
        if (userId.equals("me")) {
            userId = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        if (!authenticationService.isCurrentUser(userId, request)) {
            throw new ForbiddenException("You are not allowed to edit this user.");
        }

        userMusicService.addMusicToUser(userId, musicId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/musics/{musicId}")
    public ResponseEntity<Void> deleteUserMusic(@PathVariable("id") String userId, @PathVariable("musicId") String musicId, HttpServletRequest request) {
        if (userId.equals("me")) {
            userId = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        if (!authenticationService.isCurrentUser(userId, request)) {
            throw new ForbiddenException("You are not allowed to edit this user.");
        }

        userMusicService.removeMusicToUser(userId, musicId);
        return ResponseEntity.ok().build();
    }

    // Fossil subpfossil
    @GetMapping("/{id}/fossils")
    public ResponseEntity<Collection<APIFossil>> getUserFossils(@PathVariable("id") String id, HttpServletRequest request) {
        if (id.equals("me")) {
            id = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        return ResponseEntity.ok(userFossilService.getAllFossilByUser(id));
    }

    @PutMapping("/{id}/fossils/{fossilId}")
    public ResponseEntity<Void> addUserFossil(@PathVariable("id") String userId, @PathVariable("fossilId") String fossilId, HttpServletRequest request) {
        if (userId.equals("me")) {
            userId = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        if (!authenticationService.isCurrentUser(userId, request)) {
            throw new ForbiddenException("You are not allowed to edit this user.");
        }

        userFossilService.addFossilToUser(userId, fossilId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/fossils/{fossilId}")
    public ResponseEntity<Void> deleteUserFossil(@PathVariable("id") String userId, @PathVariable("fossilId") String fossilId, HttpServletRequest request) {
        if (userId.equals("me")) {
            userId = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        if (!authenticationService.isCurrentUser(userId, request)) {
            throw new ForbiddenException("You are not allowed to edit this user.");
        }

        userFossilService.removeFossilToUser(userId, fossilId);
        return ResponseEntity.ok().build();
    }




    // Flower subpart
    @GetMapping("/{id}/flowers")
    public ResponseEntity<Collection<APIFlower>> getUserFlowers(@PathVariable("id") String id, HttpServletRequest request) {
        if (id.equals("me")) {
            id = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        return ResponseEntity.ok(userFlowerService.getAllFlowerByUser(id));
    }

    @PutMapping("/{id}/flowers/{flowerId}")
    public ResponseEntity<Void> addUserFlower(@PathVariable("id") String userId, @PathVariable("flowerId") String flowerId, HttpServletRequest request) {
        if (userId.equals("me")) {
            userId = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        if (!authenticationService.isCurrentUser(userId, request)) {
            throw new ForbiddenException("You are not allowed to edit this user.");
        }

        userFlowerService.addFlowerToUser(userId, flowerId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/flowers/{flowerId}")
    public ResponseEntity<Void> deleteUserFlower(@PathVariable("id") String userId, @PathVariable("flowerId") String flowerId, HttpServletRequest request) {
        if (userId.equals("me")) {
            userId = authenticationService.introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId();
        }

        if (!authenticationService.isCurrentUser(userId, request)) {
            throw new ForbiddenException("You are not allowed to edit this user.");
        }

        userFlowerService.removeFlowerToUser(userId, flowerId);
        return ResponseEntity.ok().build();
    }
}