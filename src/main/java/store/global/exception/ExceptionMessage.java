package store.global.exception;

public enum ExceptionMessage {
    INPUT_PURCHASE_ITEM_NOT_DELIMITER("입력값 아이템 사이 구분자가 없습니다."),
    INPUT_PURCHASE_ITEM_NOT_SUB_PRE_FIX("입력값의 괄호가 제대로 입력 되어 있지 않습니다."),
    INPUT_PURCHASE_ITEM_EMPTY_NAME("상품명이 비어 있습니다."),
    INPUT_PURCHASE_ITEM_INVALID_QUANTITY("수량은 숫자여야 합니다."),
    FILE_INPUT_ERROR("파일의 경로를 찾을 수 없습니다.");

    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
