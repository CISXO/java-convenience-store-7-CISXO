package store.controller;

import store.service.PaymentService;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

public class PaymentController {
    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService;

    public PaymentController(StoreService storeService) {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.storeService = storeService;
    }

    public boolean purchaseStoreItem() {
        Map<String, Integer> purchaseItems = inputView.readPurchaseItem();

        processPayment(purchaseItems);

        return inputView.readContinueShopping();
    }

    public void processPayment(Map<String, Integer> purchaseItems) {
//        purchaseItems는 구매한 정보를 Map 시킨 것이다. 이를 통해 order 주문 표를 만들어야 한다.
        process(this::hasItemsValidate);
        process(this::orderPurchaseItems);

    }

    private void hasItemsValidate() {
    }

    private void orderPurchaseItems() {
    }

    private void process(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage();
        }
    }

}
