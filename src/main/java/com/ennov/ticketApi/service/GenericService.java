package com.ennov.ticketApi.service;



import java.util.List;

public interface GenericService<Entity, RequestDTO> {
    Entity save(RequestDTO dto);
    List<Entity> getAll();
    Entity getOne(Long id);
    Entity update(Long id, RequestDTO dto);
    void delete(Long id);

    Entity findById(Long id);

}