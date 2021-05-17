package ua.training.hospital.controller.diagnosisPrediction;

import com.fasterxml.jackson.databind.JsonNode;
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
import ua.training.hospital.controller.dto.DiagnosisDTO;
import ua.training.hospital.entity.DiagnosisHelpRequest;
import ua.training.hospital.entity.DiagnosisHelpRequestComment;
import ua.training.hospital.entity.exceptions.ResourceNotFoundException;
import ua.training.hospital.service.aws.AWSCaller;
import ua.training.hospital.service.diagnosisPrediction.DiagnosisHelpRequestCommentService;
import ua.training.hospital.service.diagnosisPrediction.DiagnosisHelpRequestService;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
@AllArgsConstructor
public class DiagnosisPredictionHelpController {
    private static final Logger logger = LogManager.getLogger(DignosisPredictionResultController.class);

    final private AWSCaller awsCaller;
    final private DiagnosisHelpRequestService helpRequestService;
    final private DiagnosisHelpRequestCommentService helpRequestCommentService;

    @RequestMapping(value = "/diagnosis-prediction/help{idRequest}", method = RequestMethod.GET)
    public String showHelpRequest(@PathVariable long idRequest,
                                  Model model) {

        Optional<DiagnosisHelpRequest> helpRequest = helpRequestService.getHelpRequest(idRequest);

        logger.debug("requested /diagnosis-prediction/help");

        if (helpRequest.isPresent()) {
            DiagnosisHelpRequest request = helpRequest.get();
            request.getMessages().sort(Comparator.comparing(DiagnosisHelpRequestComment::getDate));

            model.addAttribute("helpRequest", request);
        } else {
            logger.debug("can't find help request with such id");
            throw new ResourceNotFoundException();
        }

        model.addAttribute("newDiagnosis", new DiagnosisDTO());

        logger.debug("returning dignosisPrediction/diagnosisHelpRequest.jsp page");
        return "diagnoisPrediction/diagnosisHelpRequest";
    }

    @RequestMapping(value = "/diagnosis-prediction/help{idRequest}/addComment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiagnosisHelpRequestComment> addComment(@PathVariable long idRequest,
                                                                  @RequestBody String comment,
                                                                  Principal principal,
                                                                  Model model) {

        logger.debug("requested /diagnosis-prediction/help{idRequest}/addComment");

        Optional<DiagnosisHelpRequestComment> createdComment = helpRequestCommentService.createComment(comment,principal.getName(),idRequest);
        if(!createdComment.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(createdComment.get(), HttpStatus.OK);
    }
    @RequestMapping(value = "/diagnosis-prediction/help{idRequest}/getComments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DiagnosisHelpRequestComment>> getComments(@PathVariable long idRequest,
                                                                         Model model) {

        logger.debug("requested /diagnosis-prediction/help{idRequest}/getComments");

        List<DiagnosisHelpRequestComment> createdComment = helpRequestCommentService.getComments(idRequest);
        if(Objects.isNull(createdComment)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @RequestMapping(value = "/diagnosis-prediction/diagnoses-list", method = RequestMethod.GET)
    public ResponseEntity<JsonNode> getSymptomsList(Model model, @RequestParam String lang) {

        logger.debug("requested /diagnoses-list");

        Optional<JsonNode> jsonNode = awsCaller.getDiagnosesList(lang);

        if (jsonNode.isPresent()) {
            return new ResponseEntity<>(jsonNode.get(), HttpStatus.OK);
        } else {
            logger.error("AWS service call failed");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
