package ua.training.hospital.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.training.hospital.controller.utils.NameValidation;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisDTO {
    @NotEmpty(message = "{diagnosis.name.empty}")
    private String name;

    private String description;

    private List<String> symptoms;

    public DiagnosisDTO(@NotEmpty(message = "{diagnosis.name.empty}") String name, String description) {
        this.name = name;
        this.description = description;
    }
}
