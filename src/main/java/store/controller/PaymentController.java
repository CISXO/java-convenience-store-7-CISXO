package store.controller;

import store.domain.promotion.Promotion;
import store.repository.StoreRepository;
import store.service.PaymentService;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

public class PaymentController {
    private final InputView inputView;
    private final OutputView outputView;
    private final PaymentService paymentService;

    public PaymentController(StoreRepository storeRepository) {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.paymentService = new PaymentService(storeRepository);
    }

    public boolean purchaseStoreItem() {
        try {
            Map<String, Integer> purchaseItems = inputView.readPurchaseItem();
            paymentService.createOrder(purchaseItems);

            processPayment();
        } catch (IllegalStateException e) {
            outputView.printError(e.getMessage());
            purchaseStoreItem();
        }
        return inputView.readContinueShopping();
    }

    public void processPayment() {
        process(this::orderPurchaseItems);

        // 콜라 프로모션 2+1 7개, 일반재고 10개, 10개
        //현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
        //10개 구매 시 2 + 1, 2 + 1, 일단 6개 구매 가능(프로모션적용가능) 총 구매 10개, 증정 2개, 할인 불가 10 - 4개
        //Y -> 10개 구매, N -> 구매 x
        //오렌지주스 프로모션 1+1 9개, 일반 재고 0개
        //현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N) Y
        //오랜지 주스 4개 구매 시, 4개 무료로 ~ Y -> 8개 구매(프로모션 적용), N -> 4개 정가로 구매
        //오렌지 주스 5개 구매 시, 현재 오렌지 쥬스 1개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
        // 재고-> 9개 9개 구매

        //멤버십 y/N
        // 프로모션 할인이 들어간 상품은 멤버십 할인 불가능, 첫 단계에서 프로모션 적용 되었는지 확인
        // 최대 할인 금액은 8000원으로 제한

    }
    private void orderPurchaseItems() {
    }

    private void process(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e);
        }
    }

}
