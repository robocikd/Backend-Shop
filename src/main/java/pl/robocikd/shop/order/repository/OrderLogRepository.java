package pl.robocikd.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robocikd.shop.order.model.OrderLog;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {

}
