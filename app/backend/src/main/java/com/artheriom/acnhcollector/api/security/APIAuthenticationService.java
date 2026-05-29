package com.artheriom.acnhcollector.api.security;


import com.artheriom.acnhcollector.api.domain.APIUser;
import com.artheriom.acnhcollector.api.domain.dto.APIAuthDTO;
import com.artheriom.acnhcollector.api.exceptions.UnauthorizedException;
import com.artheriom.acnhcollector.api.repository.APIUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.artheriom.acnhcollector.api.Constants.CLIENT_SECRET;

@Service
public class APIAuthenticationService {

    APIUserRepository apiUserRepository;

    APIAuthenticationService(APIUserRepository apiUserRepository){
        this.apiUserRepository = apiUserRepository;
    }

    public boolean isCurrentUser(String id, HttpServletRequest request){
        boolean currentUser = false;

        if(id.equals("me")){
            currentUser = true;
        } else {
            if(request.getHeader(HttpHeaders.AUTHORIZATION)!=null && (introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).isAdmin() || introspect(request.getHeader(HttpHeaders.AUTHORIZATION)).getId().equals(id))){
                currentUser = true;
            }
        }

        return currentUser;
    }


    public String login(APIAuthDTO apiAuthDTO){
        Assert.hasText(apiAuthDTO.getUsername(), "username cannot be null");
        Assert.hasText(apiAuthDTO.getPassword(), "password cannot be null");

        //Get username in repository
        APIUser apiUser = apiUserRepository.findByUsername(apiAuthDTO.getUsername()).orElse(null);

        if(apiUser==null){
            throw new UnauthorizedException("User does not exist !");
        }

        //Check password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



        if(!passwordEncoder.matches(apiAuthDTO.getPassword(), apiUser.getPassword())){
            throw new UnauthorizedException("Password is invalid");
        }

        //Everything is ok, generating token.
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", apiUser.getId());
        map.put("emitter", "technoWeb");

        String uuid = UUID.randomUUID().toString();
        return "Bearer " + Jwts.builder()
                .setClaims(map)
                .setId(uuid)
                .setHeaderParam("kid", "technoWeb")
                .setSubject(apiUser.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 2048000000))
                .signWith(SignatureAlgorithm.HS512, CLIENT_SECRET)
                .compact();
    }


    public APIUser introspect(String token) {
        if(token==null || token.equals("")){
            throw new UnauthorizedException("Token could not be null or empty.");
        }

        APIUser apiUser;

        String[] tokenDetail = token.split(" ");

            try {
                Claims claims = Jwts.parser().setSigningKey(CLIENT_SECRET).parseClaimsJws(tokenDetail[1]).getBody();
                apiUser = apiUserRepository.findById(claims.get("user_id", String.class)).orElse(null);

            } catch (Exception e) {
                throw new UnauthorizedException("Invalid token");
            }


        if (apiUser == null) {
            throw new UnauthorizedException("User does not exists");
        }

        return apiUser;
    }

}
