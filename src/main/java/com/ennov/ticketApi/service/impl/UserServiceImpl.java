package com.ennov.ticketApi.service.impl;

import com.ennov.ticketApi.dao.PrivilegeRepository;
import com.ennov.ticketApi.dao.RoleRepository;
import com.ennov.ticketApi.dao.UserRepository;
import com.ennov.ticketApi.dto.request.UserRequestDTO;

import com.ennov.ticketApi.dto.response.SmallUserDTO;
import com.ennov.ticketApi.dto.response.UserResponseDTO;
import com.ennov.ticketApi.entities.Privilege;
import com.ennov.ticketApi.entities.Role;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.exceptions.APIException;
import com.ennov.ticketApi.exceptions.ResourceNotFoundException;
import com.ennov.ticketApi.mapper.UserMapper;
import com.ennov.ticketApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Override
    public User save(UserRequestDTO dto) {
        try{
            User user = new User();
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());
            Role role = createRoleIfNotFound("ROLE_USER");
            user.setRoles(Arrays.asList(role));
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            User saved = repository.save(user);
            System.out.println(saved);
            return saved;
        }catch (Exception e){
            throw new APIException(e.getMessage());
        }
    }


    @Override
    public List<User> getAll() {
        return repository.findAll();
    }


    @Override
    public User getOne(Long id) {
        return null;
    }

    @Override
    public User update(Long id, UserRequestDTO dto) {
        User user = findById(id);
        User existAttr = repository.findUserByEmail(dto.getEmail()).orElse(null);
        if( existAttr != null && !Objects.equals(existAttr.getId(), id)){
            throw new APIException("User with email already exists");
        }

        existAttr = repository.findUserByUsername(dto.getUsername()).orElse(null);
        if( existAttr != null && !Objects.equals(existAttr.getId(), id)){
            throw new APIException("User with username already exists");
        }

        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        return repository.save(user);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id "+id)
        );
    }



    Role createRoleIfNotFound(String name) {
       return roleRepository.getByName(name).orElse(
                roleRepository.save(new Role(name, Arrays.asList(createPrivilegeIfNotFound("READ_PRIVILEGE"))))
        );

    }

    Privilege createPrivilegeIfNotFound(String name) {
        return privilegeRepository.findByName(name).orElse(
                privilegeRepository.save(new Privilege(name))
        );

    }
}
