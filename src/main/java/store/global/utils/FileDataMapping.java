package store.global.utils;

import store.domain.product.Product;
import store.domain.promotion.Promotion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileDataMapping {

    private List<String> headers;

    public List<Product> loadProducts(String filePath) throws IOException {
        List<String> productFileList = readFile(filePath);
        initializeHeaders(productFileList.getFirst());
        return productFileList.stream()
                .skip(1)
                .map(this::mapToProduct)
                .collect(Collectors.toList());
    }

    public List<Promotion> loadPromotions(String filePath) throws IOException {
        List<String> promotionFileList = readFile(filePath);
        initializeHeaders(promotionFileList.getFirst());
        return promotionFileList.stream()
                .skip(1)
                .map(this::mapToPromotion)
                .collect(Collectors.toList());
    }

    private void initializeHeaders(String headerLine) {
        headers = Arrays.asList(headerLine.split(","));
    }

    private Product mapToProduct(String line) {
        Map<String, Object> productData = parseData(line);
        return new Product(productData);
    }

    private Promotion mapToPromotion(String line) {
        Map<String, Object> promotionData = parseData(line);
        return new Promotion(promotionData);
    }

    private Map<String, Object> parseData(String line) {
        String[] dataValues = line.split(",");
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < headers.size(); i++) {
            String key = headers.get(i);
            Object value = parseValue(dataValues[i]);
            map.put(key, value);
        }
        return map;
    }

    private Object parseValue(String value) {
        if ("null".equalsIgnoreCase(value)) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return value;
        }
    }

    private List<String> readFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }
}
