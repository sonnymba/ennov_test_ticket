package com.ennov.ticketApi.service.impl;

import com.ennov.ticketApi.dao.UserRepository;
import com.ennov.ticketApi.dto.request.UserRequestDTO;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.exceptions.ResourceNotFoundException;
import com.ennov.ticketApi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;


    @Override
    public User save(UserRequestDTO userRequestDTO) {

        return null;
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
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return repository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = getOne(id);
        repository.delete(user);
    }



    @Override
    public boolean existWithUsersame(String username) {
        return repository.findByUsername(username).isPresent();
    }

    @Override
    public boolean existWithEmail(String email) {
        return repository.findByUsername(email).isPresent();
    }
}


