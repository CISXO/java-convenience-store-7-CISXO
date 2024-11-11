package store.global.utils;

import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.global.constants.Constants;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class FileDataMapping {

    private List<String> headers;

    public List<Product> loadProducts(String filePath) throws IOException {
        List<String> productFileList = readFile(filePath);
        initializeHeaders(productFileList.get(0));

        Map<String, Product> productMap = createProductMap(productFileList);
        return new ArrayList<>(productMap.values());
    }

    public List<Promotion> loadPromotions(String filePath) throws IOException {
        List<String> promotionFileList = readFile(filePath);
        initializeHeaders(promotionFileList.get(0));

        return promotionFileList.stream()
                .skip(1)
                .map(this::mapToPromotion)
                .collect(Collectors.toList());
    }

    private Map<String, Product> createProductMap(List<String> productFileList) {
        Map<String, Product> productMap = new HashMap<>();
        productFileList.stream().skip(1).forEach(line -> processProductLine(line, productMap));
        return productMap;
    }

    private void processProductLine(String line, Map<String, Product> productMap) {
        String[] dataValues = line.split(Constants.DELIMITER);
        String key = generateProductKey(dataValues);

        if (productMap.containsKey(key)) {
            updateExistingProduct(dataValues, productMap.get(key));
            return;
        }
        addNewProduct(dataValues, productMap, key);
    }


    private String generateProductKey(String[] dataValues) {
        return dataValues[0] + dataValues[1];
    }

    private void updateExistingProduct(String[] dataValues, Product existingProduct) {
        int quantity = Integer.parseInt(dataValues[2]);
        boolean isPromotion = !"null".equals(dataValues[3]);
        existingProduct.addQuantity(quantity, isPromotion);
    }

    private void addNewProduct(String[] dataValues, Map<String, Product> productMap, String key) {
        String name = dataValues[0];
        int price = Integer.parseInt(dataValues[1]);
        int quantity = Integer.parseInt(dataValues[2]);
        String promotion = "null".equals(dataValues[3]) ? null : dataValues[3];

        int regularQuantity = promotion == null ? quantity : 0;
        int promotionQuantity = promotion != null ? quantity : 0;

        productMap.put(key, new Product(name, price, regularQuantity, promotionQuantity, promotion));
    }

    private Promotion mapToPromotion(String line) {
        String[] dataValues = line.split(Constants.DELIMITER);
        String name = dataValues[0];
        int buy = Integer.parseInt(dataValues[1]);
        int get = Integer.parseInt(dataValues[2]);
        LocalDate startDate = LocalDate.parse(dataValues[3]);
        LocalDate endDate = LocalDate.parse(dataValues[4]);

        return new Promotion(name, buy, get, startDate, endDate);
    }

    private void initializeHeaders(String headerLine) {
        headers = Arrays.asList(headerLine.split(Constants.DELIMITER));
    }

    private List<String> readFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }
}
