package store.view;

import store.domain.order.OrderItem;
import store.domain.product.Product;

import java.util.List;

public class OutputView {
    public void printExceptionMessage() {
        System.out.println("ERROR: 문제가 발생했습니다.");
    }

    public void openStore(List<Product> products) {
        printWelcomeMessage();
        products.forEach(this::printProductDetails);
    }

    private void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
    }

    private void printProductDetails(Product product) {
        if (product.getPromotionQuantity() > 0) {
            System.out.printf("- %s %,d원 %d개 %s%n",
                    product.getName(),
                    product.getPrice(),
                    product.getPromotionQuantity(),
                    product.getPromotion());
        } else if (product.getQuantity() == 0) {
            System.out.printf("- %s %,d원 재고 없음%n", product.getName(), product.getPrice());
        }

        if (product.getQuantity() > 0) {
            System.out.printf("- %s %,d원 %d개%n", product.getName(), product.getPrice(), product.getQuantity());
        } else if (product.getQuantity() == 0) {
            System.out.printf("- %s %,d원 재고 없음%n", product.getName(), product.getPrice());
        }
    }

    public void printError(String errorMessage) {
        System.out.println("[ERROR] " + errorMessage);
    }

    public void askForMembershipDiscount() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
    }

    public void showPromotionNotice(String productName, int freeItems) {
        System.out.printf("현재 %s %d개가 무료로 제공됩니다.%n", productName, freeItems);
    }

    public void showMembershipDiscount(int discount) {
        System.out.printf("멤버십 할인이 적용되었습니다: -%,d원%n", discount);
    }

    public void printReceipt(List<OrderItem> items, int finalCost, int totalDiscount) {
        System.out.println("============= W 편의점 ================");
        System.out.println("상품명\t수량\t금액");

        for (OrderItem item : items) {
            System.out.printf("%s\t%d\t%,d원%n", item.getProductName(), item.getEffectiveQuantity(), item.getTotalCost());
        }

        System.out.println("====================================");
        System.out.printf("총 구매액:\t\t%,d원%n", finalCost + totalDiscount);
        System.out.printf("할인:\t\t\t-%,d원%n", totalDiscount);
        System.out.printf("내실 돈\t\t\t%,d원%n", finalCost); // 공백을 추가하여 일치하도록 수정
    }

}
