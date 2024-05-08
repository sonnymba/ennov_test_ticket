package com.ennov.ticketApi.controller;

import com.ennov.ticketApi.config.security.jwt.JwtUtils;
import com.ennov.ticketApi.config.security.services.UserDetailsImpl;
import com.ennov.ticketApi.dto.request.AuthRequestDTO;
import com.ennov.ticketApi.dto.response.JwtResponse;
import com.ennov.ticketApi.exceptions.AuthenticationException;
import com.ennov.ticketApi.service.MainService;
import com.ennov.ticketApi.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private UserService userService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtUtils jwtUtils;


    @Autowired
    private MainService mainService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequestDTO request) {

        log.info(request.getUsername() + " " + request.getPassword());
        try {
           Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
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