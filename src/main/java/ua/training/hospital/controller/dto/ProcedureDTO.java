package ua.training.hospital.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ProcedureDTO {
    @NotEmpty(message = "{therapy.name.empty}")
    String name;

    String description;


    @Positive(message = "{procedure.room.negative}")
    int room;

    @DateTimeFormat
    List<LocalDateTime> appointmentDates;
}
