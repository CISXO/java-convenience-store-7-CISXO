package store.controller;

import camp.nextstep.edu.missionutils.Console;
import store.domain.order.Order;
import store.domain.product.Product;
import store.service.StoreService;
import store.view.OutputView;
import store.global.utils.FileDataMapping;
import store.global.constants.Constants;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PaymentController {
    private final OutputView outputView;
    private final FileDataMapping fileDataMapping;

    public PaymentController(OutputView outputView) {
        this.outputView = outputView;
        this.fileDataMapping = new FileDataMapping();
    }

    public void processPayment(Map<String, Integer> purchaseItems, StoreService storeService) {
        Order order = new Order();

        for (Map.Entry<String, Integer> entry : purchaseItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();

            try {
                List<Product> products = storeService.findProductsByName(productName);
                var promotion = storeService.findPromotionByProductName(productName);

                order.addItem(products, quantity, promotion.orElse(null));

            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                return;
            }
        }

        // 멤버십 할인 적용 여부 확인
        outputView.askForMembershipDiscount();
        String input = Console.readLine().trim().toUpperCase();
        if ("Y".equals(input)) {
            int discount = order.applyMembershipDiscount(30, 8000); // 30% 할인, 최대 8000원
            outputView.showMembershipDiscount(discount);
        }

        // 최종 영수증 출력
        outputView.printReceipt(order.getItems(), order.getFinalCost(), order.getTotalDiscount());

        // 제품 정보 최종 결제 연결
//        try {
//            fileDataMapping.updateProductsFile(storeService.getProducts(), Constants.PRODUCTS_FILE_PATH);
//        } catch (IOException e) {
//            outputView.printError("[ERROR] 제품 파일을 업데이트하는 중 문제가 발생했습니다.");
//        }

    }
}
