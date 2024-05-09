package com.ennov.ticketapi.service.impl;

import com.ennov.ticketapi.dao.PrivilegeRepository;
import com.ennov.ticketapi.dao.RoleRepository;
import com.ennov.ticketapi.dao.UserRepository;
import com.ennov.ticketapi.dto.request.UserRequestDTO;
import com.ennov.ticketapi.entities.Privilege;
import com.ennov.ticketapi.entities.Role;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.exceptions.APIException;
import com.ennov.ticketapi.exceptions.ResourceNotFoundException;
import com.ennov.ticketapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String ROLE_USER = "ROLE_USER";
    private final UserRepository repository;
    private  final RoleRepository roleRepository;
    private  final PrivilegeRepository privilegeRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public User save(UserRequestDTO dto) {
        if(repository.findByUsername(dto.getUsername()).isPresent()) throw new APIException("This username is already in use");
        if(repository.findByEmail(dto.getEmail()).isPresent()) throw new APIException("This email is already in use");
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Role role = roleRepository.findByName(ROLE_USER);
        user.setRoles(Collections.singletonList(role));
        user.setEnabled(true);
        user.setDefaultUser(false);
        return repository.save(user);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User getOne(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id " + id)
        );
    }


    @Override
    public User update(Long id, UserRequestDTO dto) {
        User user = getOne(id);
        User existWithUsername = repository.findByUsername(dto.getUsername()).orElse(null);
        if( null != existWithUsername  && !Objects.equals(existWithUsername.getId(), user.getId())) throw new APIException("This username is already in use");
        User exitEmail = repository.findByEmail(dto.getEmail()).orElse(null);
        if( null != exitEmail  && !Objects.equals(exitEmail.getId(), user.getId())) throw new APIException("This email is already in use");

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return repository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = getOne(id);
        repository.delete(user);
    }
}


