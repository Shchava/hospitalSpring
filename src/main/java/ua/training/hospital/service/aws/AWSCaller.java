package ua.training.hospital.service.aws;

import com.fasterxml.jackson.databind.JsonNode;
import ua.training.hospital.controller.diagnosisPrediction.models.PredictionResult;
import ua.training.hospital.controller.diagnosisPrediction.models.SymptomDTO;

import java.util.Optional;

public interface AWSCaller {
    Optional<JsonNode> getSymptomList(String lang);
    Optional<JsonNode> getDiagnosesList(String lang);
    Optional<PredictionResult> predictDiagnosisList(SymptomDTO symptoms, String lang);
    Optional<String> getSymptomName (String symptomIdentifier, String lang);
    Optional<String> getDiagnosisName (String diagnosisIdentifier, String lang);
}
