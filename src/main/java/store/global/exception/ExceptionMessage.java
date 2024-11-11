package store.global.exception;

public enum ExceptionMessage {
    INPUT_PURCHASE_ITEM_NOT_DELIMITER("입력값 아이템 사이 구분자가 없습니다."),
    INPUT_PURCHASE_ITEM_NOT_SUB_PRE_FIX("입력값의 괄호가 제대로 입력 되어 있지 않습니다."),
    INPUT_PURCHASE_ITEM_EMPTY_NAME("상품명이 비어 있습니다."),
    INPUT_PURCHASE_ITEM_INVALID_QUANTITY("수량은 0이상의 숫자여야 합니다."),
    INPUT_WRONG_FORM("잘못된 입력 형식입니다. 다시 입력해 주세요."),
    FILE_INPUT_ERROR("파일의 경로를 찾을 수 없습니다."),
    PRODUCT_IS_NO_EXIST("존재하지 않는 상품입니다."),
    PRPDUCT_NO_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.."),
    INPUT_Y_OR_N("Y 또는 N을 입력해 주세요.");

    private String message;

    ExceptionMessage(String message) {
        this.message = "[ERROR] " + message;
    }

    public String getMessage() {
        return message;
    }
}
