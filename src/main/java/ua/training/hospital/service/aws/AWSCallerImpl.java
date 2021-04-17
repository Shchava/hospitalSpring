package ua.training.hospital.service.aws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.training.hospital.controller.diagnosisPrediction.models.PredictionResult;
import ua.training.hospital.controller.diagnosisPrediction.models.SymptomDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AWSCallerImpl implements AWSCaller {
    private static final String SYMPTOMS_LIST_API_URL = "https://3ncxlmxrbd.execute-api.us-east-1.amazonaws.com/prod2/get-symptoms-list";
    private static final String DIAGNOSIS_PREDICT_API_URL = "https://3ncxlmxrbd.execute-api.us-east-1.amazonaws.com/prod2/perdict-diagnosis";
    private static final int AWS_REQUEST_TIMEOUT = 3000;
    private static final String langRequestPattern = "{\n\"lang\": \"%s\"\n}";

    private ObjectMapper objectMapper;

    @Override
    public Optional<JsonNode> getSymptomList(String lang) {
        try {
            URL url = new URL(SYMPTOMS_LIST_API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("charset", "utf-8");
            con.setDoOutput(true);
            con.setConnectTimeout(AWS_REQUEST_TIMEOUT);

            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(String.format(langRequestPattern, lang));
            writer.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;

            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            return Optional.ofNullable(objectMapper.readTree(content.toString()));


        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<PredictionResult>  predictDiagnosisList(SymptomDTO symptoms, String lang) {
        try {
            URL url = new URL(DIAGNOSIS_PREDICT_API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("charset", "utf-8");
            con.setDoOutput(true);
            con.setConnectTimeout(AWS_REQUEST_TIMEOUT);

            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(objectMapper.writeValueAsString(symptoms));
            writer.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;

            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            //todo: replace with actual logic
            return Optional.of(new PredictionResult("testDisiease", 0.1));



        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
