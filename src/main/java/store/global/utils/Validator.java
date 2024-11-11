package store.global.utils;

import store.global.constants.Constants;
import store.global.exception.ExceptionMessage;

public class Validator {


    public static void readPurchaseInput(String input) {
        if (!input.startsWith(Constants.PRODUCTS_PREFIX) || !input.endsWith(Constants.PRODUCTS_SUFFIX)) {
            throw new IllegalArgumentException(ExceptionMessage.INPUT_PURCHASE_ITEM_NOT_SUB_PRE_FIX.getMessage());
        }

        String content = input.substring(1, input.length() - 1);
        String[] parts = content.split(Constants.CONTENT_DELIMITER);

        if (parts.length != 2) {
            throw new IllegalArgumentException(ExceptionMessage.INPUT_PURCHASE_ITEM_NOT_DELIMITER.getMessage());
        }

        if (parts[0].trim().isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessage.INPUT_PURCHASE_ITEM_EMPTY_NAME.getMessage());
        }

        try {
            Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ExceptionMessage.INPUT_PURCHASE_ITEM_INVALID_QUANTITY.getMessage());
        }
    }

    public static void validateRemainingQuantity(int remainingQuantity) {
        if (remainingQuantity > 0) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

}
