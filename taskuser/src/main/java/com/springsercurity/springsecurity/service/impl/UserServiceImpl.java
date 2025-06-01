package com.springsercurity.springsecurity.service.impl;

import com.springsercurity.springsecurity.dto.UserDTO;
import com.springsercurity.springsecurity.dto.request.UpdateUserRequest;
import com.springsercurity.springsecurity.dto.request.UpdateUserRoleRequest;
import com.springsercurity.springsecurity.entity.User;
import com.springsercurity.springsecurity.entity.mapper.UserMapper;
import com.springsercurity.springsecurity.repository.UserRepository;
import com.springsercurity.springsecurity.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDTO(user);
    }


    @Override
    public UserDTO updateUser(Integer id, UpdateUserRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Chỉ ADMIN mới được đổi ROLE (nếu bạn cần kiểm tra, có thể inject Auth info ở đây)
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());

        // Nếu có truyền password mới thì cần mã hóa lại
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword())); // thêm dòng này
        }

        return UserMapper.toDTO(userRepository.save(existing));
    }


    @Override
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO updateUserRole(Integer id, UpdateUserRoleRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(request.getRole());
        return UserMapper.toDTO(userRepository.save(user));
    }

}
