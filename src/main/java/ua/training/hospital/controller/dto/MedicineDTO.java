package ua.training.hospital.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;


@Getter
@Setter
public class MedicineDTO {
    @NotEmpty(message = "{therapy.name.empty}")
    String name;

    String description;


    @Positive(message = "{medicine.count.negative}")
    @NotNull(message = "{medicine.count.null}")
    Integer count;

    LocalDate refill;
}
