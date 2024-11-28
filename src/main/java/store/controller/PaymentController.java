package store.controller;

import store.domain.order.Order;
import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.global.exception.ExceptionMessage;
import store.service.PaymentService;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

public class PaymentController {
    private final InputView inputView;
    private final OutputView outputView;
    private final PaymentService paymentService;

    public PaymentController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.paymentService = new PaymentService();
    }

    public void processPayment(Map<String, Integer> purchaseItems) {

    }

}
