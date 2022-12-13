package pl.robocikd.shop.admin.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.robocikd.shop.admin.order.model.AdminOrder;
import pl.robocikd.shop.admin.order.model.AdminOrderStatus;
import pl.robocikd.shop.admin.order.repositor.AdminOrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminExportService {

    private final AdminOrderRepository adminOrderRepository;

    public List<AdminOrder> exportOrders(LocalDateTime from, LocalDateTime to, AdminOrderStatus orderStatus) {

        return adminOrderRepository.findAllByPlaceDateIsBetweenAndOrderStatus(from, to, orderStatus);
    }
}
