package com.ennov.ticketApi.service.impl;

import com.ennov.ticketApi.dao.UserRepository;
import com.ennov.ticketApi.dto.request.UserRequestDTO;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.exceptions.APIException;
import com.ennov.ticketApi.exceptions.ResourceNotFoundException;
import com.ennov.ticketApi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(UserRequestDTO dto) {
        if(repository.findByUsername(dto.getUsername()).isPresent()) throw new APIException("This username is already in use");
        if(repository.findByEmail(dto.getEmail()).isPresent()) throw new APIException("This email is already in use");
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

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


