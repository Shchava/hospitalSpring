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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.training.hospital.controller.diagnosisPrediction.models.PredictionResult;
import ua.training.hospital.controller.diagnosisPrediction.models.SymptomDTO;
import ua.training.hospital.service.aws.AWSCaller;
import ua.training.hospital.service.user.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class DignosisPredictionResultController {
    private static final Logger logger = LogManager.getLogger(DignosisPredictionResultController.class);




    @RequestMapping(value = "/diagnosis-prediction/askHelp", method = RequestMethod.POST)
    public String predictDiagnosis(@ModelAttribute("prediction") @Valid PredictionResult prediction,
                                   BindingResult result,
                                   Model model) {

        model.addAttribute("prediction", prediction);


        Optional<PredictionResult> response;
        return "diagnoisPrediction/diagnosisPredictionResultPage";
    }
}
