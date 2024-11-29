package store.controller;

import store.domain.product.Product;
import store.domain.product.Products;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.global.constants.Constants;
import store.global.utils.FileDataMapping;
import store.service.StoreService;
import store.view.OutputView;

import java.util.*;

public class StoreController {
    private final OutputView outputView;
    private final StoreService storeService;
    private final PaymentController paymentController;

    public StoreController() {
        this.outputView = new OutputView();
        this.storeService = new StoreService();
        this.paymentController = new PaymentController(storeService);
    }

    public void run() {
        process(this::initializeProducts);
        process(this::initializePromotions);
        process(this::startShopping);
    }

    private void initializeProducts() {
        List<Product> products = loadProducts();
        storeService.saveProducts(new Products(products));
    }

    private void initializePromotions() {
        List<Promotion> promotions = loadPromotions();
        storeService.savePromotions(new Promotions(promotions));
    }

    private void startShopping() {
        boolean continueShopping = true;

        while (continueShopping) {
            openStore();
            continueShopping = paymentController.purchaseStoreItem();
        }
    }

    private void openStore() {
        List<Product> products = storeService.getProducts();
        outputView.openStore(products);
    }

    private List<Product> loadProducts(){
        return new FileDataMapping().loadProducts(Constants.PRODUCTS_FILE_PATH);
    }

    private List<Promotion> loadPromotions() {
        return new FileDataMapping().loadPromotions(Constants.PROMOTIONS_FILE_PATH);
    }

    private void process(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage();
        }
    }

}
