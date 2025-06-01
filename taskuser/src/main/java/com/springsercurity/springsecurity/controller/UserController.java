package com.springsercurity.springsecurity.controller;

import com.springsercurity.springsecurity.annotation.CheckPermission;
import com.springsercurity.springsecurity.annotation.OnlySelfAccess;
import com.springsercurity.springsecurity.dto.UserDTO;
import com.springsercurity.springsecurity.dto.request.UpdateUserRequest;
import com.springsercurity.springsecurity.dto.request.UpdateUserRoleRequest;
import com.springsercurity.springsecurity.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;

    @CheckPermission
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(iUserService.getAllUsers());
    }

    @CheckPermission
    @OnlySelfAccess(userIdParam = "id")
    @GetMapping("/getById/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(iUserService.getUserById(id));
    }

    @CheckPermission
    @OnlySelfAccess(userIdParam = "id")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(iUserService.updateUser(id, request));
    }

    @CheckPermission
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        iUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @CheckPermission
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/update-role/{id}")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Integer id, @RequestBody UpdateUserRoleRequest request) {
        return ResponseEntity.ok(iUserService.updateUserRole(id, request));
    }

}
