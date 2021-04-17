package ua.training.hospital.controller.diagnosisPrediction.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PredictionResult {
    private String name;
    private Double accuracy;
}
