package store.controller;

import store.domain.order.Order;
import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.global.exception.ExceptionMessage;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

public class PaymentController {
    private final InputView inputView;
    private final OutputView outputView;

    public PaymentController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void processPayment(Map<String, Integer> purchaseItems, StoreService storeService) {
        Order order = new Order();
        purchaseItems.forEach((productName, quantity) -> processPurchaseItem(storeService, order, productName, quantity));
        applyMembershipDiscountIfApplicable(order);
        finalizeOrder(storeService, order);
    }

    private void processPurchaseItem(StoreService storeService, Order order, String productName, int quantity) {
        Product product = storeService.getProducts(productName);
        Promotion promotion = storeService.findPromotionByProductName(productName);

        if (isQuantityExceedingStock(product, quantity)) return;

        if (promotion == null || !promotion.isPromotionActive()) {
            order.addItem(product, quantity, null);
            return;
        }
        processPromotion(order, product, promotion, quantity);
    }

    private boolean isQuantityExceedingStock(Product product, int quantity) {
        int totalAvailableStock = product.getQuantity() + product.getPromotionQuantity();
        if (quantity > totalAvailableStock) {
            outputView.printError(ExceptionMessage.PRPDUCT_NO_QUANTITY.getMessage());
            return true;
        }
        return false;
    }

    private void processPromotion(Order order, Product product, Promotion promotion, int quantity) {
        int availablePromoQuantity = product.getPromotionQuantity();
        if (quantity <= availablePromoQuantity) {
            applyFullPromotion(order, product, promotion, quantity);
            return;
        }
        applyPartialPromotion(order, product, promotion, quantity, availablePromoQuantity);
    }

    private void applyFullPromotion(Order order, Product product, Promotion promotion, int quantity) {
        int effectiveQuantity = calculateEffectiveQuantity(quantity, promotion);
        int promoDiscount = calculatePromotionDiscount(product, effectiveQuantity, quantity);

        if (getAddExtraItemsConfirmation(product.getName(), effectiveQuantity - quantity)) {
            addPromotionItemsToOrder(order, product, effectiveQuantity, promotion, promoDiscount);
            return;
        }
        order.addItem(product, quantity, promotion);
    }

    private void applyPartialPromotion(Order order, Product product, Promotion promotion, int quantity, int promoStock) {
        int promoSetSize = promotion.getBuyQuantity() + promotion.getFreeQuantity();
        int fullPromoSets = promoStock / promoSetSize;
        int remainder = promoStock % promoSetSize;

        int totalItemsWithoutDiscount = calculateItemsWithoutDiscount(quantity, promoStock, remainder);
        showPartialPromoNotice(product.getName(), promoStock, quantity, totalItemsWithoutDiscount);

        if (inputView.readYesNoConfirmation()) {
            int promoDiscount = fullPromoSets * product.getPrice() * promotion.getFreeQuantity();
            addPromotionItemsToOrder(order, product, quantity, promotion, promoDiscount);
            return;
        }
        order.addItem(product, promoStock, promotion);
    }

    private int calculateItemsWithoutDiscount(int quantity, int promoStock, int remainder) {
        return quantity - promoStock + remainder;
    }

    private void showPartialPromoNotice(String productName, int promoStock, int quantity, int itemsWithoutDiscount) {
        outputView.showPartialPromoNotice(productName, promoStock, quantity, itemsWithoutDiscount);
    }

    private void addPromotionItemsToOrder(Order order, Product product, int quantity, Promotion promotion, int promoDiscount) {
        order.addItem(product, quantity, promotion);
        order.addPromotionDiscount(promoDiscount);
    }

    private boolean getAddExtraItemsConfirmation(String productName, int extraItems) {
        outputView.showPromotionNotice(productName, extraItems);
        return inputView.readYesNoConfirmation();
    }

    private int calculateEffectiveQuantity(int quantity, Promotion promotion) {
        return quantity + (quantity / promotion.getBuyQuantity()) * promotion.getFreeQuantity();
    }

    private int calculatePromotionDiscount(Product product, int effectiveQuantity, int quantity) {
        return (effectiveQuantity - quantity) * product.getPrice();
    }

    private void applyMembershipDiscountIfApplicable(Order order) {
        if (order.getTotalPromotionDiscount() < order.getTotalRegularCost()) {
            applyMembershipDiscount(order);
        }
    }

    private void applyMembershipDiscount(Order order) {
        outputView.askForMembershipDiscount();
        if (inputView.readMembershipDiscountConfirmation()) {
            int discount = order.applyMembershipDiscount(30, 8000);
            outputView.showMembershipDiscount(discount);
        }
    }

    private void finalizeOrder(StoreService storeService, Order order) {
        storeService.updateProductInventory(order.getItems());
        outputView.printReceipt(order.getItems(), order.getFinalCost(), order.getTotalRegularCost(),
                order.getMembershipDiscount(), order.getTotalPromotionDiscount());
    }
}
