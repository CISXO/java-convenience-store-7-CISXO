package store.domain.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record OrderItems(List<OrderItem> orderItems) {

    public OrderItems(List<OrderItem> orderItems) {
        this.orderItems = new ArrayList<>(orderItems);
        for (OrderItem orderItem : orderItems) {
            this.orderItems.add(new OrderItem(orderItem.getOrderProductInfo(),
                    orderItem.getOrderItemName(),
                    orderItem.getOrderItemQuantity()
            ));
        }
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }
}