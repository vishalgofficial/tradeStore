package com.db.tredestore.util;

import com.db.tredestore.controller.TradeController;
import com.db.tredestore.domain.TradeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    public static List<TradeRequest> buildMockTradeRequest(String inputFileName) {
        List<TradeRequest> tradeRequests = new ArrayList<>();
        try {
            ClassLoader classLoader = TradeController.class.getClassLoader();
            URL fileUrl = classLoader.getResource(inputFileName);
            if (fileUrl != null) {
                File inputFile = new File(fileUrl.getFile());
                String inputJson = FileUtils.readFileToString(inputFile, StandardCharsets.UTF_8);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                tradeRequests = Arrays.asList(mapper.readValue(inputJson, TradeRequest[].class));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tradeRequests;
    }
}
