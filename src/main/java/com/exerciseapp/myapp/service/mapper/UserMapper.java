package com.exerciseapp.myapp.service.mapper;

import com.exerciseapp.myapp.domain.User;
import com.exerciseapp.myapp.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {}
