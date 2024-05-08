package com.ennov.ticketApi.service;

import com.ennov.ticketApi.dao.UserRepository;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MainService {

    @Autowired
    public UserRepository userRepository;

    public User getCurrentUser() throws ResourceNotFoundException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return new User();
        }
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return  userRepository.findByUsername(userPrincipal.getUsername()).orElseThrow(
                () ->  new ResourceNotFoundException("User not found with username " + userPrincipal.getUsername())
        );
    }

}
