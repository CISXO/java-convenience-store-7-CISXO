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
    private final PaymentController paymentController;

    public StoreController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.storeService = new StoreService();
        this.paymentController = new PaymentController(outputView);
    }

    public void run() {
        try {
            initializeLoadAllFiles();
            startShopping();
        } catch (IOException e) {
            outputView.printExceptionMessage();
        }
    }

    private void startShopping() throws IOException {
        boolean continueShopping = true;

        while (continueShopping) {
            openStore();              // 상품 목록을 보여줍니다.
            continueShopping = purchaseStoreItem(); // 구매 프로세스를 시작합니다.
        }
    }

    private void openStore() {
        List<Product> products = storeService.getProducts();
        outputView.openStore(products);
    }

    private boolean purchaseStoreItem() {
        Map<String, Integer> purchaseItems = inputView.readPurchaseItem();

        // 결제 및 할인 로직을 PaymentController로 위임
        paymentController.processPayment(purchaseItems, storeService);

        // 추가 구매 여부를 묻는 부분을 InputView로 위임
        return inputView.readContinueShopping();
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
