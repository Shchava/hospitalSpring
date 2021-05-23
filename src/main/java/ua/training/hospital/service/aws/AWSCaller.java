package ua.training.hospital.service.aws;

import com.fasterxml.jackson.databind.JsonNode;
import ua.training.hospital.controller.diagnosisPrediction.models.HumanPredictionDto;
import ua.training.hospital.controller.diagnosisPrediction.models.PredictionResult;
import ua.training.hospital.controller.diagnosisPrediction.models.SymptomDTO;

import java.util.Optional;

public interface AWSCaller {
    Optional<JsonNode> getSymptomList(String lang);
    Optional<JsonNode> getDiagnosesList(String lang);
    Optional<PredictionResult> predictDiagnosis(SymptomDTO symptoms, String lang);
    Optional<String> getSymptomName (String symptomIdentifier, String lang);
    Optional<String> getDiagnosisName (String diagnosisIdentifier, String lang);
    Boolean saveDiagnosisPrediction (HumanPredictionDto prediction);
    Boolean saveDiagnosisPredictionByPredictionRequestId (String diagnosisName, long predictionHelpRequestId);
}
