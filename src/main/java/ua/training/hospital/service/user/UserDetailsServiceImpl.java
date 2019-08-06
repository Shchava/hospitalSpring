package ua.training.hospital.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.UserAuthentication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUser(email)
                .orElseThrow(()-> new UsernameNotFoundException("user with email "+ email + " not found"));

        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        return new UserAuthentication(
                user.getIdUser(),
                user.getEmail(),
                user.getPasswordHash(),
                roles);
    }
}
