package store.controller;

import store.domain.product.Product;
import store.domain.product.Products;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.global.constants.Constants;
import store.global.utils.FileDataMapping;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.io.IOException;
import java.util.*;

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
            initializeLoadAllFiles();
        } catch (IOException e) {
            outputView.printExceptionMessage();
            return;
        }
        List<Product> products = storeService.getProducts();

        outputView.openStore(products);

    }

    private void initializeLoadAllFiles() throws IOException {
        initializeProducts();
        initializePromotions();
    }

    private void initializeProducts() throws IOException {
        List<Product> products = loadProducts();
        storeService.saveProducts(new Products(products));
    }

    private void initializePromotions() throws IOException {
        List<Promotion> promotions = loadPromotions();
        storeService.savePromotions(new Promotions(promotions));
    }

    private List<Product> loadProducts() throws IOException {
        return new FileDataMapping().loadProducts(Constants.PRODUCTS_FILE_PATH);
    }

    private List<Promotion> loadPromotions() throws IOException {
        return new FileDataMapping().loadPromotions(Constants.PROMOTIONS_FILE_PATH);
    }
}