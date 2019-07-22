package ua.training.hospital.service.user;

import ua.training.hospital.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String email);
}
