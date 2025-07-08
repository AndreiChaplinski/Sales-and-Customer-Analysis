package com.internship.service;

import com.internship.models.Customer;
import com.internship.models.Order;
import com.internship.models.OrderItem;
import com.internship.models.OrderStatus;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SalesMetricsService {

    public static Set<String> getUniqueCities(List<Order> orders) {
        return orders.stream().map(order -> order.getCustomer().getCity()).collect(Collectors.toSet());
    }


    public static double getTotalPrice(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.DELIVERED))
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }


    public static String getTheMostPopularItem(List<Order> orders) {
        Map<String, Integer> productSales = orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.DELIVERED))
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getProductName, Collectors.summingInt(OrderItem::getQuantity)));
        return  productSales.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }


    public static double getTheAverageCheck(List<Order> orders) {
        long deliveredCount = orders.stream().filter(order -> order.getStatus().equals(OrderStatus.DELIVERED)).count();
        return deliveredCount > 0 ? getTotalPrice(orders) / deliveredCount : 0.0;
    }


    public static Set<Customer> getCustomersWithMoreThenFiveOrders(List<Order> orders) {
        Map<Customer, Long> orderCounts = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting()));
        return orderCounts.entrySet().stream()
                .filter(customer -> customer.getValue() > 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
