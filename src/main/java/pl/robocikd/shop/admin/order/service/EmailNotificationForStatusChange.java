package pl.robocikd.shop.admin.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.robocikd.shop.admin.order.controller.model.AdminOrder;
import pl.robocikd.shop.admin.order.controller.model.AdminOrderStatus;
import pl.robocikd.shop.common.mail.EmailClientService;

import static pl.robocikd.shop.admin.order.service.AdminOrderEmailMessage.createCompletedEmailMessage;
import static pl.robocikd.shop.admin.order.service.AdminOrderEmailMessage.createProcessingEmailMessage;
import static pl.robocikd.shop.admin.order.service.AdminOrderEmailMessage.createRefundEmailMessage;

@Service
@RequiredArgsConstructor
class EmailNotificationForStatusChange {

    private final EmailClientService emailClientService;

    public void sendEmailNotification(AdminOrderStatus newStatus, AdminOrder adminOrder) {
        if (newStatus == AdminOrderStatus.PROCESSING) {
            sendEmail(
                    adminOrder.getEmail(),
                    "Zamównienie " + adminOrder.getId() + " zmeniło status na: " + newStatus.getValue(),
                    createProcessingEmailMessage(adminOrder.getId(), newStatus)
            );
        } else if (newStatus == AdminOrderStatus.COMPLETED) {
            sendEmail(
                    adminOrder.getEmail(),
                    "Zamównienie " + adminOrder.getId() + " zostało zrealizowane",
                    createCompletedEmailMessage(adminOrder.getId(), newStatus)
            );
        } else if (newStatus == AdminOrderStatus.REFUND) {
            sendEmail(
                    adminOrder.getEmail(),
                    "Zamównienie " + adminOrder.getId() + " zostało zwrócone",
                    createRefundEmailMessage(adminOrder.getId(), newStatus)
            );
        }
    }

    private void sendEmail(String email, String subject, String message) {
        emailClientService.getInstance().send(email, subject, message);
    }
}
