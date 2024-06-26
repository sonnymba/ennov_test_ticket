package com.ennov.ticketapi.service.impl;

import com.ennov.ticketapi.dao.RoleRepository;
import com.ennov.ticketapi.dao.UserRepository;
import com.ennov.ticketapi.entities.Role;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.exceptions.APIException;
import com.ennov.ticketapi.exceptions.ResourceNotFoundException;

import com.ennov.ticketapi.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class MainServiceImpl implements MainService {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public final UserRepository userRepository;

    public final RoleRepository roleRepository;

    public MainServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public User getCurrentUser() throws ResourceNotFoundException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return  userRepository.findByUsername(userPrincipal.getUsername()).orElse(null);
    }

    public User makeAdmin(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        Role role = roleRepository.getByName(ROLE_ADMIN).orElseThrow(() -> new ResourceNotFoundException("role " + ROLE_ADMIN + " not found"));
        if(user.getRoles().contains(role))
            throw new APIException("The user already has the role "+role.getName());

        user.getRoles().add(role);
        return userRepository.save(user);
    }

    public User removeAdmin(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id "+id));
        if(user.isDefaultUser())
            throw new APIException("You cannot change the default user role");

        Role role = roleRepository.getByName(ROLE_ADMIN).orElseThrow(() -> new ResourceNotFoundException("role "+ROLE_ADMIN+" not found"));
        if(user.getRoles().contains(role)){
            user.getRoles().remove(role);
            return userRepository.save(user);
        }else{
            throw new APIException("The user have not the role "+role.getName());
        }
    }


    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

}
