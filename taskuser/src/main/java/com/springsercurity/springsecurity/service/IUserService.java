package com.springsercurity.springsecurity.service;

import com.springsercurity.springsecurity.dto.UserDTO;
import com.springsercurity.springsecurity.dto.request.UpdateUserRequest;
import com.springsercurity.springsecurity.dto.request.UpdateUserRoleRequest;

import java.util.List;

public interface IUserService {
    public List<UserDTO> getAllUsers();

    public UserDTO getUserById(Integer id);

    public UserDTO updateUser(Integer id, UpdateUserRequest request);

    public void deleteUser(Integer id);

    public UserDTO updateUserRole(Integer id, UpdateUserRoleRequest request);
}
