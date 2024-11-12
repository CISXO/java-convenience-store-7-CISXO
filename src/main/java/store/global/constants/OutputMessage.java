package store.global.constants;

public enum OutputMessage {
    WELCOME("안녕하세요. W편의점입니다."),
    CURRENT_PRODUCTS("현재 보유하고 있는 상품입니다."),
    ERROR_MESSAGE("ERROR: 문제가 발생했습니다."),
    MEMBERSHIP_PROMPT("멤버십 할인을 받으시겠습니까? (Y/N)"),
    PROMOTION_NOTICE("현재 %s %d개가 무료로 제공됩니다."),
    MEMBERSHIP_DISCOUNT_APPLIED("멤버십 할인이 적용되었습니다: -%,d원\n"),
    RECEIPT_HEADER("\n============= W 편의점 ================"),
    RECEIPT_TOTAL_INFO("상품명\t수량\t금액\n"),
    RECEIPT_ITEM_FORMAT("%s\t%d\t%,d원\n"),
    RECEIPT_FOOTER("===================================="),
    TOTAL_COST("총 구매액:\t\t%,d원\n"),
    PROMOTION_DISCOUNT("행사할인:\t\t\t-%,d원\n"),
    MEMBERSHIP_DISCOUNT("멤버십 할인:\t\t\t-%,d원\n"),
    FINAL_AMOUNT("내실 돈\t\t\t%,d원\n"),
    OUT_OF_STOCK("재고 없음"),
    PRODUCT_WITH_PROMOTION("- %s %,d원 %d개 %s%n"),
    PRODUCT_IN_STOCK("- %s %,d원 %d개%n"),
    PRODUCT_OUT_OF_STOCK("- %s %,d원 %s%n"),
    SHOW_PROMOTION_NOTICE("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n"),
    SHOW_PARTITIAL_NOTICE("현재 %s %d개 중 %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n");

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
