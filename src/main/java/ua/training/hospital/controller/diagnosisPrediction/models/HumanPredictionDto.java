package ua.training.hospital.controller.diagnosisPrediction.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HumanPredictionDto {
    String diagnosis;
    List<String> symptoms;
}
