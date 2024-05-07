package com.ennov.ticketApi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CrudController<RequestDTO, Entity> {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<?> save(@Valid @RequestBody RequestDTO dto);

    @GetMapping
    List<Entity> list();

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody RequestDTO dto);


}
