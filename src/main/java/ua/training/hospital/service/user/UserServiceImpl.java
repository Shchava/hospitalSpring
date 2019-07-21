package ua.training.hospital.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.User;
import ua.training.hospital.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public User getUser(String email) {
        return repository.findByEmail(email);
    }
}
