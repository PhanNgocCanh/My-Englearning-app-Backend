package com.exerciseapp.myapp.service.impl;

import com.exerciseapp.myapp.common.constants.ErrorConstant;
import com.exerciseapp.myapp.common.exception.CommonException;
import com.exerciseapp.myapp.domain.Role;
import com.exerciseapp.myapp.domain.User;
import com.exerciseapp.myapp.repository.RoleRepository;
import com.exerciseapp.myapp.repository.UserRepository;
import com.exerciseapp.myapp.service.UserService;
import com.exerciseapp.myapp.service.dto.UserDTO;
import com.exerciseapp.myapp.service.mapper.UserMapper;
import com.exerciseapp.myapp.utils.EncryptUtil;
import java.util.HashSet;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, RoleRepository roleRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(UserDTO userDTO) {
        try {
            // validate user dto
            if (userDTO.getEmail().trim().equals("")) {
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.EMAIL_BLANK);
            }
            boolean userIsExists = userRepository.existsByEmail(userDTO.getEmail());
            if (userIsExists) {
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.USER_ALREADY_EXISTS);
            }

            userDTO.setPassword(EncryptUtil.Encrypt(userDTO.getPassword()));
            User user = userMapper.toEntity(userDTO);
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName("ROLE_ADMIN"));
            user.setRoles(roles);

            userRepository.save(user);
        } catch (Exception e) {
            throw CommonException.create(HttpStatus.INTERNAL_SERVER_ERROR).code(ErrorConstant.INTERNAL_SERVER_ERROR);
        }
    }
}
