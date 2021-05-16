package ua.training.hospital.controller.diagnosisPrediction;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.training.hospital.service.aws.AWSCaller;

import java.util.Optional;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
@Log4j2
public class DiagnosisPredictionSymptomsTranslator {
    private AWSCaller awsCaller;

    @RequestMapping(value = "/diagnosis-prediction/symptom-translation", method = RequestMethod.GET)
    public ResponseEntity<String> getSymptomsList(
            @RequestParam String symptomIdentifier,
            @RequestParam String lang) {

        log.debug("requested /diagnosis-prediction/symptom-translation");

        Optional<String> name = awsCaller.getSymptomName(symptomIdentifier, lang);

        if (name.isPresent()) {
            return new ResponseEntity<>(name.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
