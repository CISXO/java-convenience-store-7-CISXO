package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.global.constants.Constants;
import store.global.utils.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class InputView {

    private String readInput() {
        return Console.readLine();
    }

    public Map<String, Integer> readPurchaseItem() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String input = readInput();
        return parseInputWithRetry(input);
    }

    private Map<String, Integer> parseInputWithRetry(String input) {
        try {
            return parsePurchaseInput(input);
        } catch (Exception e) {
            System.out.println("[ERROR] 잘못된 입력 형식입니다. 다시 입력해 주세요.");
            return readPurchaseItem();
        }
    }

    private Map<String, Integer> parsePurchaseInput(String input) {
        Map<String, Integer> itemsMap = new HashMap<>();
        StringTokenizer purchaseItems = new StringTokenizer(input, Constants.DELIMITER);
        while (purchaseItems.hasMoreTokens()) {
            String purchaseItem = purchaseItems.nextToken().trim();
            Validator.readPurchaseInput(purchaseItem);
            addItemToMap(purchaseItem, itemsMap);
        }
        return itemsMap;
    }

    private void addItemToMap(String itemSet, Map<String, Integer> itemsMap) {
        String content = itemSet.substring(1, itemSet.length() - 1);
        String[] parts = content.split(Constants.CONTENT_DELIMITER);

        String productName = parts[0].trim();
        int quantity = Integer.parseInt(parts[1].trim());
        itemsMap.put(productName, quantity);
    }

    public boolean readContinueShopping() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        String input = readInput().trim().toUpperCase();

        while (!input.equals("Y") && !input.equals("N")) {
            System.out.println("[ERROR] 올바르지 않은 입력입니다. Y 또는 N을 입력해 주세요.");
            input = readInput().trim().toUpperCase();
        }

        return input.equals("Y");
    }
}
