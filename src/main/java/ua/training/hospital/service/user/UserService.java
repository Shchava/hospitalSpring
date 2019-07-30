package ua.training.hospital.service.user;

import ua.training.hospital.controller.dto.UserDTO;
import ua.training.hospital.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String email);
    Optional<User> registerUser(UserDTO userDto);
}
