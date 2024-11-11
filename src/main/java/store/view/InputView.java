package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.global.constants.Constants;
import store.global.exception.ExceptionMessage;
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
            System.out.println(e.getMessage());
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
        return readShoppingRedirect();
    }

    private boolean readShoppingRedirect() {
        String input = readInput().trim().toUpperCase();

        while (!input.equals(Constants.YES) && !input.equals(Constants.NO)) {
            System.out.println(ExceptionMessage.INPUT_WRONG_FORM.getMessage());
            System.out.println("Y 또는 N을 입력해 주세요.");
            input = readInput().trim().toUpperCase();
        }
        return input.equals(Constants.YES);
    }

    public boolean readMembershipDiscountConfirmation() {
        return readShoppingRedirect();
    }

    public boolean readYesNoConfirmation() {
        while (true) {
            String input = readInput().trim().toUpperCase();
            if (input.equals(Constants.YES)) return true;
            if (input.equals(Constants.NO)) return false;

            System.out.println("[ERROR] Y 또는 N만 입력 가능합니다. 다시 입력해 주세요.");
        }
    }


}
