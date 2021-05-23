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
import java.util.Objects;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AWSCallerImpl implements AWSCaller {
    private static final String SYMPTOMS_LIST_API_URL = "https://3ncxlmxrbd.execute-api.us-east-1.amazonaws.com/prod2/get-symptoms-list";
    private static final String DIAGNOSES_LIST_API_URL = "https://3ncxlmxrbd.execute-api.us-east-1.amazonaws.com/prod2/get-diagnoses-list";
    private static final String GET_SYMPTOM_NAME_API_URL = "https://3ncxlmxrbd.execute-api.us-east-1.amazonaws.com/prod2/get-symptom-name";
    private static final String GET_DIAGNOSIS_NAME_API_URL = "https://3ncxlmxrbd.execute-api.us-east-1.amazonaws.com/prod2/get-diagnosis-name";
    private static final String DIAGNOSIS_PREDICT_API_URL = "https://3ncxlmxrbd.execute-api.us-east-1.amazonaws.com/prod2/perdict-diagnosis";
    private static final int AWS_REQUEST_TIMEOUT = 3000;
    private static final String langRequestPattern = "{\n\"lang\": \"%s\"\n}";
    public static final String SYMPTOM_IDENTIFIER_PARAM = "symptomIdentifier";
    public static final String DIAGNOSIS_IDENTIFIER_PARAM = "diagnosisIdentifier";
    public static final String LANG_PARAM = "lang";
    public static final String STATUS_CODE = "statusCode";
    public static final String NAME = "name";

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
    public Optional<JsonNode> getDiagnosesList(String lang) {
        try {

            String requestUrl = DIAGNOSES_LIST_API_URL +
                    "?" + LANG_PARAM + "=" + lang;
            URL url = new URL(requestUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("charset", "utf-8");
            con.setDoOutput(true);
            con.setConnectTimeout(AWS_REQUEST_TIMEOUT);

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

            JsonNode response = objectMapper.readTree(content.toString());
            if(Objects.isNull(response) || response.findValue("statusCode").asInt() != 200) {
                return Optional.empty();
            }

            JsonNode responseBody = response.findValue("body");
            if(Objects.isNull(responseBody)) {
                return Optional.empty();
            }

            PredictionResult result = new PredictionResult();
            result.setName(responseBody.findValue("diagnosis").textValue());
            result.setAccuracy(responseBody.findValue("probability").doubleValue());
            result.setSymptoms(symptoms.getSymptoms());

            return Optional.of(result);



        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    public static void main(String[] args) {
        new AWSCallerImpl(new ObjectMapper()).getSymptomName("itching", "ua");
    }

    @Override
    public Optional<String> getSymptomName(String SymptomIdentifier, String lang) {
        try {
            String requestUrl = GET_SYMPTOM_NAME_API_URL +
                    "?" + SYMPTOM_IDENTIFIER_PARAM + "=" + SymptomIdentifier +
                    "&" + LANG_PARAM + "=" + lang;
            URL url = new URL(requestUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("charset", "utf-8");
            con.setDoOutput(true);
            con.setConnectTimeout(AWS_REQUEST_TIMEOUT);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;

            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            JsonNode response = objectMapper.readTree(content.toString());
            if(response.findValue(STATUS_CODE).asInt() == 200) {
                return Optional.of(response.findValue(NAME).asText());
            } else {
                // TODO: add log
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getDiagnosisName(String diagnosisIdentifier, String lang) {
        try {
            String requestUrl = GET_DIAGNOSIS_NAME_API_URL +
                    "?" + DIAGNOSIS_IDENTIFIER_PARAM + "=" + diagnosisIdentifier +
                    "&" + LANG_PARAM + "=" + lang;
            URL url = new URL(requestUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("charset", "utf-8");
            con.setDoOutput(true);
            con.setConnectTimeout(AWS_REQUEST_TIMEOUT);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;

            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            JsonNode response = objectMapper.readTree(content.toString());
            if(response.findValue(STATUS_CODE).asInt() == 200) {
                return Optional.of(response.findValue(NAME).asText());
            } else {
                // TODO: add log
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
