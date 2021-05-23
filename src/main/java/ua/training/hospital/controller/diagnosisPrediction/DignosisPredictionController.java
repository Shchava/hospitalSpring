package ua.training.hospital.controller.diagnosisPrediction;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.training.hospital.controller.diagnosisPrediction.models.PredictionResult;
import ua.training.hospital.controller.diagnosisPrediction.models.SymptomDTO;
import ua.training.hospital.service.aws.AWSCaller;
import ua.training.hospital.service.user.UserService;

import java.util.Optional;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class DignosisPredictionController {
    private static final Logger logger = LogManager.getLogger(DignosisPredictionController.class);

    UserService userService;

    ObjectMapper objectMapper;

    AWSCaller awsCaller;


    @RequestMapping(value = "/diagnosis-prediction", method = RequestMethod.GET)
    public String defaultShowPatient(Model model) {

        logger.debug("requested /diagnosis-prediction");

        logger.debug("returning dignosisPrediction/diagnosisPredictionPage.jsp page");
        return "diagnoisPrediction/diagnosisPredictionPage";
    }

    @RequestMapping(value = "/diagnosis-prediction/symptoms-list", method = RequestMethod.GET)
    public ResponseEntity<JsonNode> getSymptomsList(Model model, @RequestParam String lang) {

        logger.debug("requested /symptoms-list");

        Optional<JsonNode> jsonNode = awsCaller.getSymptomList(lang);

        if (jsonNode.isPresent()) {
            return new ResponseEntity<>(jsonNode.get(), HttpStatus.OK);
        } else {
            logger.error("AWS service call failed");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/diagnosis-prediction/predict", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String predictDiagnosis(@RequestParam String symptoms, Model model) {

        Optional<PredictionResult> response;

        try {
            SymptomDTO symptomDTO = objectMapper.readValue(symptoms, SymptomDTO.class);
            response = awsCaller.predictDiagnosis(symptomDTO, "ua");
            model.addAttribute("newDiagnosis", new PredictionResult());
            response.ifPresent(
                    predictionResult -> {
                        predictionResult.setSymptoms(symptomDTO.getSymptoms());
                        model.addAttribute("prediction", predictionResult);
                    }
            );

        } catch (Exception ex) {

        }

        logger.debug("requested /diagnosis-prediction");

        logger.debug("returning dignosisPrediction/diagnosisPredictionPage.jsp page");
        return "diagnoisPrediction/diagnosisPredictionResultPage";
    }
}
