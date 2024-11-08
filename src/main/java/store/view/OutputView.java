package store.view;

import store.domain.product.Product;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class OutputView {

    public void printExceptionMessage() {
        System.out.println("ERROR");
    }

    public void openStore(List<Product> products) {
        printWelcomeMessage();
        Map<String, Integer> productNameCount = countProductNames(products);
        products.forEach(product -> printProductDetails(product, productNameCount));
    }

    private void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
    }

    private Map<String, Integer> countProductNames(List<Product> products) {
        Map<String, Integer> productNameCount = new HashMap<>();
        for (Product product : products) {
            String productName = getProductName(product);
            productNameCount.put(productName, productNameCount.getOrDefault(productName, 0) + 1);
        }
        return productNameCount;
    }

    private String getProductName(Product product) {
        return (String) product.getProductData().get("name");
    }

    private void printProductDetails(Product product, Map<String, Integer> productNameCount) {
        String productName = getProductName(product);
        StringBuilder productOutput = buildProductOutput(product);
        appendQuantityAndPromotion(product, productOutput);

        if (productNameCount.get(productName) == 1) {
            printSingleProduct(productOutput, product);
        } else {
            System.out.println(productOutput);
        }
    }

    private StringBuilder buildProductOutput(Product product) {
        StringBuilder productOutput = new StringBuilder();
        String productName = getProductName(product);
        productOutput.append("- ").append(productName).append(" ")
                .append(String.format("%,d", product.getProductData().get("price"))).append("원 ");
        return productOutput;
    }

    private void appendQuantityAndPromotion(Product product, StringBuilder productOutput) {
        int quantity = (Integer) product.getProductData().get("quantity");
        if (quantity > 0) {
            productOutput.append(quantity).append("개");
        } else {
            productOutput.append("재고 없음");
        }

        String promotion = (String) product.getProductData().get("promotion");
        if (promotion != null && !promotion.equals("null")) {
            productOutput.append(" ").append(promotion);
        }
    }

    private void printSingleProduct(StringBuilder productOutput, Product product) {
        System.out.println(productOutput);
        productOutput = buildProductOutput(product);
        productOutput.append("재고 없음");
        System.out.println(productOutput);
    }

}