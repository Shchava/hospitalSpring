package ua.training.hospital.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShowUserToDoctorDTO {
    long id;
    String name;
    String surname;
    String patronymic;
    String email;
    String lastDiagnosisName;
}
