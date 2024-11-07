package store.controller;

import store.domain.product.Product;
import store.domain.product.Products;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.global.constants.Constants;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService;

    public StoreController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.storeService = new StoreService();
    }

    public void run() {
        try {
            initializeRoadAllFile();
        } catch (IOException e) {
            outputView.printExceptionMessage();
            return;
        }

//        while (false) {
//
//        }
    }


    private void initializeRoadAllFile() throws IOException {
        initializeProduct();
        initializePromotion();
    }

    private void initializeProduct() throws IOException {
        List<String> productFileList = readFile(Constants.PRODUCTS_FILE_PATH);
        List<Product> products = mappingProducts(productFileList);
        storeService.saveProducts(new Products(products));
    }

    private void initializePromotion() throws IOException {
        List<String> promotionFileList = readFile(Constants.PROMOTIONS_FILE_PATH);
        List<Promotion> promotions = mappingPromotions(promotionFileList);
        storeService.savePromotions(new Promotions(promotions));
    }

    private List<Product> mappingProducts(List<String> productFileList) {
        return productFileList.stream()
                .skip(1)
                .map(line -> {
                    Map<String, Object> productData = parsingProductData(line); // String을 Map으로 변환
                    return new Product(productData);
                })
                .collect(Collectors.toList());
    }

    private List<Promotion> mappingPromotions(List<String> promotionFileList) {
        return promotionFileList.stream()
                .skip(1)
                .map(line -> {
                    Map<String, Object> promotionData = parsingData(line); // String을 Map으로 변환
                    return new Promotion(promotionData);
                })
                .collect(Collectors.toList());
    }

    private Map<String, Object> parsingProductData(String line) {
        String[] productData = line.split(",");
        Map<String, Object> map = new HashMap<>();
        map.put("name", productData[0]);
        map.put("price", Integer.parseInt(productData[1]));
        map.put("quantity", Integer.parseInt(productData[2]));
        map.put("promotion", productData[3]);
        return map;
    }

    private Map<String, Object> parsingData(String line) {
        String[] promotionData = line.split(",");
        Map<String, Object> map = new HashMap<>();
        map.put("name", promotionData[0]);
        map.put("buy", Integer.parseInt(promotionData[1]));
        map.put("get", Integer.parseInt(promotionData[2]));
        map.put("startDate", LocalDate.parse(promotionData[3]));
        map.put("endDate", LocalDate.parse(promotionData[4]));
        return map;
    }

    private List<String> readFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }
}
