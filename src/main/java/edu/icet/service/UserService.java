package edu.icet.service;

import edu.icet.dto.UserDTO;

public interface UserService {
    Boolean existsByEmail(String email);
    UserDTO saveUser(UserDTO userDTO);
    UserDTO getUserByEmail(String email);
}
