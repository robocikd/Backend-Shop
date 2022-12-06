package pl.robocikd.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robocikd.shop.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
