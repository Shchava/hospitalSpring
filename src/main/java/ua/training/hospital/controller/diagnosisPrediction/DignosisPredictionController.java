package ua.training.hospital.controller.diagnosisPrediction;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.training.hospital.controller.diagnosisPrediction.models.SymptomDTO;
import ua.training.hospital.service.user.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class DignosisPredictionController {
    private static final Logger logger = LogManager.getLogger(DignosisPredictionController.class);
    public static final String SYMPTOMS_LIST_API_URL = "https://3ncxlmxrbd.execute-api.us-east-1.amazonaws.com/prod2/get-symptoms-list";
    public static final int AWS_REQUEST_TIMEOUT = 3000;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;


    @RequestMapping(value = "/diagnosis-prediction", method = RequestMethod.GET)
    public String defaultShowPatient(Model model) {

        logger.debug("requested /diagnosis-prediction");

        logger.debug("returning dignosisPrediction/diagnosisPredictionPage.jsp page");
        return "diagnoisPrediction/diagnosisPredictionPage";
    }

    @RequestMapping(value = "/diagnosis-prediction/symptoms-list", method = RequestMethod.GET)
    public ResponseEntity<JsonNode> getSymptomsList(Model model) {

        logger.debug("requested /symptoms-list");

        JsonNode jsonNode = null;
        try {
            URL url = new URL(SYMPTOMS_LIST_API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("charset", "utf-8");
            con.setDoOutput(true);
            con.setConnectTimeout(AWS_REQUEST_TIMEOUT);

            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write("{\n" +
                    "\"lang\": \"ua\"\n" +
                    "}");
            writer.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;

            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            jsonNode = objectMapper.readTree(content.toString());


        } catch (IOException e) {
            e.printStackTrace();

            return new ResponseEntity<>(jsonNode, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(jsonNode, HttpStatus.OK);
    }

    @RequestMapping(value = "/diagnosis-prediction/predict", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String predictDiagnosis(@RequestParam String symptoms) {

        ObjectMapper objectMapper = new ObjectMapper();


        try {
            SymptomDTO dto = objectMapper.readValue(symptoms, SymptomDTO.class);
            System.out.println(dto);
        } catch (Exception ex) {

        }





        logger.debug("requested /diagnosis-prediction");

        logger.debug("returning dignosisPrediction/diagnosisPredictionPage.jsp page");
        return "showPatient";
    }
}
