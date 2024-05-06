package com.ennov.ticketApi.controller;

import com.ennov.ticketApi.dto.response.EntityResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CrudController<RequestDTO, ResponseDTO, DetailsDTO> {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityResponse> save(@Valid @RequestBody RequestDTO dto);

    @GetMapping("/{id}")
    DetailsDTO getOne(@PathVariable("id") Long id);

    @GetMapping
    List<ResponseDTO> list();

    @PutMapping("/{id}")
    ResponseEntity<EntityResponse> update(@Valid @RequestBody RequestDTO dto, @PathVariable("id") Long id);


    @DeleteMapping("/{id}")
    ResponseEntity<EntityResponse> delete(@PathVariable(name = "id") Long id);

}
