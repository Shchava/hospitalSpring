package ua.training.hospital.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CreationResponse {
    String message;
    List<ObjectError> errors;
}
