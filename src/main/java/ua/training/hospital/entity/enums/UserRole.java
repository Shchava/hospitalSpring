package ua.training.hospital.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    PATIENT, DOCTOR, NURSE;

    @Override
    public String getAuthority() {
        return name();
    }
}
