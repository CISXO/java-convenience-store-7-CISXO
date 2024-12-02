package store.service;

import store.domain.order.OrderItem;
import store.domain.promotion.Promotion;
import store.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaymentService {
    private final StoreRepository storeRepository;
    OrderItem orderItem;
    List<OrderItem> orderItemList = new ArrayList<>();
    public PaymentService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void createOrder(Map<String, Integer> purchaseItems) {
        for (Map.Entry<String, Integer> entry : purchaseItems.entrySet()) {
            String orderItemName = entry.getKey();
            Integer orderItemQuantity = entry.getValue();
            orderItem = storeRepository.createOrderItem(orderItemName, orderItemQuantity);
            orderItemList.add(orderItem);
        }
        storeRepository.createOrders(orderItemList);

    }

    public void isPromotionValid() {
        // 주문상태 order에 저장할때 해당하는 상품의 존재 또는 상품의 재고 개수가 유호한지 확인
        // 주문상태를 넣을 때 해당하는 상품의 이름을 통해서 Product에 대한 정보를 가져오고 그에 맞는 Promotion을 찾아와서 OrderItem에 저장해야 함
        // order에서 product에 대한 정보를 바탕으로 promotion 계산 로직을 처리하고 뽑아줄 것들을 뽑아주어야 함
        //멤버십 여부를 확인하고 멤버십을 진행하는데 isPromotion이라는 프로모션을 적용했는지 확인하여 멤버십과 할인이 중복되지 않게 처리해야함
        // 멤버십 할인은 최대 8000원까지 30%로 진행됨
    }
}
