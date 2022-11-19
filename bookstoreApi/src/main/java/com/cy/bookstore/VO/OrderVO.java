package com.cy.bookstore.VO;

import com.cy.bookstore.entity.Order;
import com.cy.bookstore.entity.OrderItem;

import java.util.List;
import java.util.Objects;

public class OrderVO extends Order {

    private List<OrderItem> orderItems;  // 添加了这一项

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "orderItems=" + orderItems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderVO)) return false;
        if (!super.equals(o)) return false;
        OrderVO orderVO = (OrderVO) o;
        return Objects.equals(getOrderItems(), orderVO.getOrderItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOrderItems());
    }
}
