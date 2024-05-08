package com.ennov.ticketapi.controller;

import com.ennov.ticketapi.config.security.jwt.JwtUtils;
import com.ennov.ticketapi.config.security.services.UserDetailsImpl;
import com.ennov.ticketapi.dto.request.AuthRequestDTO;
import com.ennov.ticketapi.dto.response.JwtResponse;
import com.ennov.ticketapi.exceptions.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/account")
@CrossOrigin("*")
@Slf4j
public class AccountController {


    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtUtils jwtUtils;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequestDTO request) {

        try {
           Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(new JwtResponse(
                    jwt, "Bearer",
                    userDetails.getId(),
                    userDetails.getUsername(),
                    roles
            ));
        } catch (Exception ex) {
            throw new AuthenticationException(ex.getMessage());
        }
    }




}