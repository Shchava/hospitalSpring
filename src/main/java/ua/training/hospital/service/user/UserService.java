package ua.training.hospital.service.user;

import ua.training.hospital.entity.User;

public interface UserService {
    User getUser(String email);
}
