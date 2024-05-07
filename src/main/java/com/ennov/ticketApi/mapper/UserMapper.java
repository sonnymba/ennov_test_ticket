package com.ennov.ticketApi.mapper;

import com.ennov.ticketApi.dto.request.UserRequestDTO;

import com.ennov.ticketApi.dto.response.SmallUserDTO;
import com.ennov.ticketApi.dto.response.UserResponseDTO;
import com.ennov.ticketApi.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserRequestDTO, UserResponseDTO, SmallUserDTO> {
}
