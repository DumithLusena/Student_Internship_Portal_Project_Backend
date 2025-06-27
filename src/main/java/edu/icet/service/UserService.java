package edu.icet.service;

import edu.icet.dto.UserDTO;

import java.util.List;

public interface UserService {
    Boolean existsByEmail(String email);
    UserDTO saveUser(UserDTO userDTO);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    UserDTO getUser(Long id);
    void deleteUser(Long id);
}
