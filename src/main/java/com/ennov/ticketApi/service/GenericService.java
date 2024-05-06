package com.ennov.ticketApi.service;

import com.ennov.ticketApi.dto.response.DetailsTicketResponseDTO;
import com.ennov.ticketApi.dto.response.EntityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenericService<Entity, RequestDTO, ResponseDTO, DetailsDTO> {
    ResponseEntity<EntityResponse> save(RequestDTO dto);
    List<ResponseDTO> getAll();
    DetailsDTO getOne(Long id);
    ResponseEntity<EntityResponse> update(RequestDTO dto, Long id);
    ResponseEntity<EntityResponse> delete(Long id);

    Entity findById(Long id);

}