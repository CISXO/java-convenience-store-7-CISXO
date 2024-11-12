package store.global.utils;

import store.global.constants.Constants;
import store.global.exception.ExceptionMessage;

public class Validator {

    public static void readPurchaseInput(String input) {
        validatePrefixSuffix(input);
        String content = extractContent(input);
        String[] parts = splitContent(content);

        validateProductName(parts[0]);
        validateQuantity(parts[1]);
    }

    private static void validatePrefixSuffix(String input) {
        if (!input.startsWith(Constants.PRODUCTS_PREFIX) || !input.endsWith(Constants.PRODUCTS_SUFFIX)) {
            throw new IllegalArgumentException(ExceptionMessage.INPUT_PURCHASE_ITEM_NOT_SUB_PRE_FIX.getMessage());
        }
    }

    private static String extractContent(String input) {
        return input.substring(1, input.length() - 1);
    }

    private static String[] splitContent(String content) {
        String[] parts = content.split(Constants.CONTENT_DELIMITER);
        if (parts.length != 2) {
            throw new IllegalArgumentException(ExceptionMessage.INPUT_PURCHASE_ITEM_NOT_DELIMITER.getMessage());
        }
        return parts;
    }

    private static void validateProductName(String name) {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessage.INPUT_PURCHASE_ITEM_EMPTY_NAME.getMessage());
        }
    }

    private static void validateQuantity(String quantityPart) {
        try {
            int quantity = Integer.parseInt(quantityPart.trim());
            if (quantity <= 0) {
                throw new IllegalArgumentException(ExceptionMessage.INPUT_PURCHASE_ITEM_INVALID_QUANTITY.getMessage());
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ExceptionMessage.INPUT_PURCHASE_ITEM_INVALID_QUANTITY.getMessage());
        }
    }

    public static void validateRemainingQuantity(int remainingQuantity) {
        if (remainingQuantity > 0) {
            throw new IllegalArgumentException(ExceptionMessage.PRPDUCT_NO_QUANTITY.getMessage());
        }
    }

}
