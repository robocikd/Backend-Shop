package pl.robocikd.shop.admin.order.repositor;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robocikd.shop.admin.order.controller.model.AdminOrder;

public interface AdminOrderRepository extends JpaRepository<AdminOrder, Long> {
}
