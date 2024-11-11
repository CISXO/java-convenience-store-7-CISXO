package store.domain.product;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private int promotionQuantity;
    private final String promotion;

    public Product(String name, int price, int quantity, int promotionQuantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionQuantity = promotionQuantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public void reduceQuantity(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException("Insufficient stock for product: " + name);
        }
        this.quantity -= amount;
    }

    public void reducePromotionQuantity(int amount) {
        if (amount > promotionQuantity) {
            throw new IllegalArgumentException("Insufficient promotion stock for product: " + name);
        }
        this.promotionQuantity -= amount;
    }

    public void addQuantity(int quantity, boolean isPromotion) {
        if (isPromotion) {
            this.promotionQuantity += quantity;
        } else {
            this.quantity += quantity;
        }
    }
}
