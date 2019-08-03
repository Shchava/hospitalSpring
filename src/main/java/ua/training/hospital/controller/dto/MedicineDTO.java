package ua.training.hospital.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Getter
@Setter
public class MedicineDTO {
    @NotEmpty(message = "{therapy.name.empty}")
    String name;

    String description;

    @NotNull(message = "{medicine.count.null}")
    int count;

    LocalDate refill;
}
