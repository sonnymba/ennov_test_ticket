package com.ennov.ticketapi.controller;

import com.ennov.ticketapi.config.security.jwt.JwtUtils;
import com.ennov.ticketapi.config.security.services.UserDetailsImpl;
import com.ennov.ticketapi.dto.RoleDTO;
import com.ennov.ticketapi.dto.request.AuthRequestDTO;
import com.ennov.ticketapi.dto.response.JwtResponse;
import com.ennov.ticketapi.dto.response.LiteUserDTO;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.exceptions.APIException;
import com.ennov.ticketapi.exceptions.AuthenticationException;
import com.ennov.ticketapi.service.MainService;
import lombok.extern.slf4j.Slf4j;

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


    private final AuthenticationManager authManager;

    private final MainService mainService;

    private final JwtUtils jwtUtils;

    public AccountController(AuthenticationManager authManager, MainService mainService, JwtUtils jwtUtils) {
        this.authManager = authManager;
        this.mainService = mainService;
        this.jwtUtils = jwtUtils;
    }


    /**
     * Login
     */

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

    /**
     * Lister les roles de rôle
     */
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> roles() {
        List<RoleDTO> roles = mainService.getRoles().stream().map(RoleDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }


    /**
     * Nommer un User Admin
     */
    @PutMapping("/{id}/make-admin")
    public ResponseEntity<LiteUserDTO> makeAdmin(@PathVariable Long id) {
        if(id == null) throw new APIException("id is required");

        User user = mainService.makeAdmin(id);
        LiteUserDTO userDTO = new LiteUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }


    /**
     * Retirer le rôme Admin à un User
     */
    @PutMapping("/{id}/remove-admin")
    public ResponseEntity<LiteUserDTO> removeAdmin(@PathVariable Long id) {
        if(id == null) throw new APIException("id is required");
        User user = mainService.removeAdmin(id);
        LiteUserDTO userDTO = new LiteUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

}