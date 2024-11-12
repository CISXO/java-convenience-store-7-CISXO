package store.view;

import store.domain.order.OrderItem;
import store.domain.product.Product;
import store.global.constants.OutputMessage;
import store.global.exception.ExceptionMessage;

import java.util.List;

public class OutputView {
    private void printMessage(String msg) { System.out.println(msg); }

    public void openStore(List<Product> products) {
        printWelcomeMessage();
        products.forEach(this::printProductDetails);
    }

    private void printWelcomeMessage() {
        printMessage(OutputMessage.WELCOME.getMessage());
        printMessage(OutputMessage.CURRENT_PRODUCTS.getMessage());
    }

    private void printProductDetails(Product product) {
        if (product.getPromotionQuantity() > 0) {
            printProductWithPromotion(product);
        }
        if (product.getQuantity() > 0) {
            printProductInStock(product);
            return;
        }
        printProductOutOfStock(product);
    }

    private void printProductWithPromotion(Product product) {
        System.out.printf(OutputMessage.PRODUCT_WITH_PROMOTION.getMessage(),
                product.getName(),
                product.getPrice(),
                product.getPromotionQuantity(),
                product.getPromotion());
    }

    private void printProductInStock(Product product) {
        System.out.printf(OutputMessage.PRODUCT_IN_STOCK.getMessage(), product.getName(), product.getPrice(), product.getQuantity());
    }

    private void printProductOutOfStock(Product product) {
        System.out.printf(OutputMessage.PRODUCT_OUT_OF_STOCK.getMessage(), product.getName(), product.getPrice(), OutputMessage.OUT_OF_STOCK.getMessage());
    }

    public void printError(String errorMessage) {
        printMessage(errorMessage);
    }

    public void askForMembershipDiscount() {
        printMessage(OutputMessage.MEMBERSHIP_PROMPT.getMessage());
    }

    public void showMembershipDiscount(int discount) {
        System.out.printf(OutputMessage.MEMBERSHIP_DISCOUNT_APPLIED.getMessage(), discount);
    }

    public void printReceipt(List<OrderItem> items, int finalCost,int totalRegularCost ,int memberShipDiscount, int promotionDiscount) {
        printMessage(OutputMessage.RECEIPT_HEADER.getMessage());

        for (OrderItem item : items) {
            System.out.printf(OutputMessage.RECEIPT_ITEM_FORMAT.getMessage(), item.getProductName(), item.getQuantity(), item.getTotalCost());
        }
        printMessage(OutputMessage.RECEIPT_FOOTER.getMessage());
        System.out.printf(OutputMessage.TOTAL_COST.getMessage(), totalRegularCost);
        System.out.printf(OutputMessage.PROMOTION_DISCOUNT.getMessage(), promotionDiscount);
        System.out.printf(OutputMessage.MEMBERSHIP_DISCOUNT.getMessage(), memberShipDiscount);
        System.out.printf(OutputMessage.FINAL_AMOUNT.getMessage(), finalCost);
    }


    public void printExceptionMessage() {

        printMessage(ExceptionMessage.FILE_INPUT_ERROR.getMessage());
    }

    public void showPromotionNotice(String productName, int freeItems) {
        System.out.printf(OutputMessage.SHOW_PROMOTION_NOTICE.getMessage(), productName, freeItems);
    }

    public void showPartialPromoNotice(String productName, int promoStock, int quantity, int totalItemsWithoutDiscount) {
        System.out.printf(OutputMessage.SHOW_PARTITIAL_NOTICE.getMessage(),
                productName, quantity, totalItemsWithoutDiscount);
    }

}
