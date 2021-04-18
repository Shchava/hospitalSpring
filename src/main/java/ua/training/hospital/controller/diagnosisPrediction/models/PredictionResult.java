package ua.training.hospital.controller.diagnosisPrediction.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredictionResult {
    private String name;
    private Double accuracy;
    private List<String> symptoms;
    private String comments;

    public PredictionResult(String name, Double accuracy) {
        this.name = name;
        this.accuracy = accuracy;
    }
}
