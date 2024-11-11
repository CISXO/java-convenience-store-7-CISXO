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

        Map<String, Product> productMap = new HashMap<>();
        productFileList.stream().skip(1).forEach(line -> addProductToMap(line, productMap));

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

//    public void updateProductsFile(List<Product> products, String filePath) throws IOException {
//        List<String> lines = new ArrayList<>();
//        lines.add("name,price,quantity,promotion");
//
//        products.forEach(product -> addProductLine(lines, product));
//        Files.write(Paths.get(filePath), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
//    }

    private void addProductToMap(String line, Map<String, Product> productMap) {
        String[] dataValues = line.split(Constants.DELIMITER);
        String name = dataValues[0];
        int price = Integer.parseInt(dataValues[1]);
        int quantity = Integer.parseInt(dataValues[2]);
        String promotion = "null".equals(dataValues[3]) ? null : dataValues[3];
        String key = name + price;

        if (productMap.containsKey(key)) {
            Product existingProduct = productMap.get(key);
            if (promotion == null) {
                existingProduct.addQuantity(quantity, false);
            } else {
                existingProduct.addQuantity(quantity, true);
            }
        } else {
            int regularQuantity = promotion == null ? quantity : 0;
            int promotionQuantity = promotion != null ? quantity : 0;
            productMap.put(key, new Product(name, price, regularQuantity, promotionQuantity, promotion));
        }
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
        headers = Arrays.asList(headerLine.split(","));
    }

    private List<String> readFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    private void addProductLine(List<String> lines, Product product) {
        if (product.getQuantity() > 0) {
            lines.add(String.format("%s,%d,%d,null",
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity()
            ));
        }
        if (product.getPromotionQuantity() > 0) {
            lines.add(String.format("%s,%d,%d,%s",
                    product.getName(),
                    product.getPrice(),
                    product.getPromotionQuantity(),
                    product.getPromotion() == null ? "" : product.getPromotion()
            ));
        }
    }
}
