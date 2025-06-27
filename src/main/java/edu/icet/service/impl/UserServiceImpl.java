package edu.icet.service.impl;

import edu.icet.dto.UserDTO;
import edu.icet.entity.User;
import edu.icet.repository.UserRepository;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        if (existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setCompanyName(userDTO.getCompanyName());
        user.setCourse(userDTO.getCourse());
        user.setInstitution(userDTO.getInstitution());
        user.setPhone(userDTO.getPhone());

        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDTO::new)
                .orElse(null);
    }
}
