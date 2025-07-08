package com.internship.models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String orderId;
    private LocalDateTime orderDate;
    private Customer customer;
    private List<OrderItem> items;
    private OrderStatus status;

    public String getOrderId() {
        return orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Order(String orderId, LocalDateTime orderDate, Customer customer, List<OrderItem> items, OrderStatus status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.items = items;
        this.status = status;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

