package pl.robocikd.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robocikd.shop.order.model.OrderRow;

public interface OrderRowRepository extends JpaRepository<OrderRow, Long> {
}
