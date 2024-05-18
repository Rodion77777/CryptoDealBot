package CryptoDealBot.demo.business;

import CryptoDealBot.demo.domain.entity.CryptoPairEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ResponseJsonParser {

    public static double getMarkPrice(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        return rootNode.get("markPrice").asDouble();
    }

    public static CryptoPairEntity parseJsonToCryptoPairEntity(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, CryptoPairEntity.class);
    }
}
