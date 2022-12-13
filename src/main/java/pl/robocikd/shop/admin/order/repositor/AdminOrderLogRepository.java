package pl.robocikd.shop.admin.order.repositor;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robocikd.shop.admin.order.model.AdminOrderLog;

public interface AdminOrderLogRepository extends JpaRepository<AdminOrderLog, Long> {
}
