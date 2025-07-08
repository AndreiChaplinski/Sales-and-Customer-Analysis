import com.internship.models.*;
import com.internship.service.SalesMetricsService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SalesMetricsTest {

    private final LocalDateTime now = LocalDateTime.now();

    @Test
    void getUniqueCities_returnsCorrectCities() {

        Customer customer1 = new Customer("1", "Andrei", "andrei@gmail.com", now, 22, "New York");
        Customer customer2 = new Customer("2", "Katya", "andrei@gmail.com", now, 25, "London");
        Customer customer3 = new Customer("3", "Rob", "andrei@gmail.com", now, 35, "New York");

        List<Order> orders = Arrays.asList(
                new Order("1", now, customer1, Collections.emptyList(), OrderStatus.DELIVERED),
                new Order("2", now, customer2, Collections.emptyList(), OrderStatus.DELIVERED),
                new Order("3", now, customer3, Collections.emptyList(), OrderStatus.DELIVERED)
        );

        Set<String> result = SalesMetricsService.getUniqueCities(orders);

        assertEquals(2, result.size());
        assertTrue(result.contains("New York"));
        assertTrue(result.contains("London"));
    }

    @Test
    void getTotalPrice_calculatesDeliveredOrdersOnly() {
        Customer customer = new Customer("1", "Andrei", "andrei@gmail.com", now, 22, "New York");

        List<OrderItem> items = Arrays.asList(
                new OrderItem("Laptop", 1, 1000.0, Category.ELECTRONICS),
                new OrderItem("Mouse", 2, 25.0, Category.ELECTRONICS)
        );

        List<Order> orders = Arrays.asList(
                new Order("1", now, customer, items, OrderStatus.DELIVERED),
                new Order("2", now, customer, items, OrderStatus.CANCELLED)
        );
        
        double result = SalesMetricsService.getTotalPrice(orders);

        double expected = (1 * 1000.0) + (2 * 25.0);
        assertEquals(expected, result, 0.001);
    }

    @Test
    void getTheMostPopularItem_returnsItemWithHighestQuantity() {

        Customer customer = new Customer("1", "Andrei", "andrei@gmail.com", now, 22, "New York");

        List<OrderItem> items1 = Arrays.asList(
                new OrderItem("Laptop", 1, 1000.0, Category.ELECTRONICS),
                new OrderItem("Mouse", 3, 25.0, Category.ELECTRONICS)
        );

        List<OrderItem> items2 = Arrays.asList(
                new OrderItem("Mouse", 4, 25.0, Category.ELECTRONICS),
                new OrderItem("Keyboard", 2, 50.0, Category.ELECTRONICS)
        );

        List<Order> orders = Arrays.asList(
                new Order("o1", now, customer, items1, OrderStatus.DELIVERED),
                new Order("o2", now, customer, items2, OrderStatus.DELIVERED),
                new Order("o3", now, customer, items2, OrderStatus.CANCELLED)
        );

        String result = SalesMetricsService.getTheMostPopularItem(orders);

        assertEquals("Mouse", result);
    }

    @Test
    void getTheAverageCheck_calculatesCorrectAverage() {

        Customer customer = new Customer("1", "Andrei", "andrei@gmail.com", now, 22, "New York");

        List<OrderItem> items = Collections.singletonList(
                new OrderItem("Laptop", 1, 1000.0, Category.ELECTRONICS)
        );

        List<Order> orders = Arrays.asList(
                new Order("o1", now, customer, items, OrderStatus.DELIVERED),
                new Order("o2", now, customer, items, OrderStatus.DELIVERED),
                new Order("o3", now, customer, items, OrderStatus.CANCELLED)
        );

        double result = SalesMetricsService.getTheAverageCheck(orders);

        assertEquals(1000.0, result, 0.001);
    }

    @Test
    void getCustomersWithMoreThenFiveOrders_filtersCorrectly() {

        Customer customer1 = new Customer("1", "Andrei", "andrei@gmail.com", now, 22, "New York");
        Customer customer2 = new Customer("2", "Katya", "andrei@gmail.com", now, 25, "London");

        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            orders.add(new Order("1-" + i, now, customer1, Collections.emptyList(), OrderStatus.DELIVERED));
        }

        for (int i = 0; i < 5; i++) {
            orders.add(new Order("2-" + i, now, customer2, Collections.emptyList(), OrderStatus.DELIVERED));
        }

        Set<Customer> result = SalesMetricsService.getCustomersWithMoreThenFiveOrders(orders);

        assertEquals(1, result.size());
        assertTrue(result.contains(customer1));
    }

    @Test
    void edgeCases_handleEmptyInputs() {
        List<Order> emptyOrders = Collections.emptyList();

        assertTrue(SalesMetricsService.getUniqueCities(emptyOrders).isEmpty());
        assertEquals(0.0, SalesMetricsService.getTotalPrice(emptyOrders), 0.001);
        assertNull(SalesMetricsService.getTheMostPopularItem(emptyOrders));
        assertEquals(0.0, SalesMetricsService.getTheAverageCheck(emptyOrders), 0.001);
        assertTrue(SalesMetricsService.getCustomersWithMoreThenFiveOrders(emptyOrders).isEmpty());
    }

    @Test
    void getTheAverageCheck_handlesZeroDeliveredOrders() {

        Customer customer = new Customer("1", "Andrei", "andrei@gmail.com", now, 22, "New York");

        List<Order> orders = Arrays.asList(
                new Order("1", now, customer, Collections.emptyList(), OrderStatus.CANCELLED),
                new Order("2", now, customer, Collections.emptyList(), OrderStatus.PROCESSING)
        );

        double result = SalesMetricsService.getTheAverageCheck(orders);

        assertEquals(0.0, result, 0.001);
    }
}