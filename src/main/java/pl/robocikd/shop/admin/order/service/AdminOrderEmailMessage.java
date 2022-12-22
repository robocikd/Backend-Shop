package pl.robocikd.shop.admin.order.service;

import pl.robocikd.shop.common.model.OrderStatus;

public class AdminOrderEmailMessage {

    public static String createProcessingEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamównienie: " + id + " jest przetwarzane." +
                "\nStatus został zmnieniony na: " + newStatus.getValue() +
                "\nTwoje zamównienie jest przetwarzane przez naszych pracowników" +
                "\nPo skompletowaniu niezwłocznie przekażemy je do wysyłki" +
                "\n\nPozdrawiamy" +
                "\nSklep Shop";
    }

    public static String createCompletedEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamównienie: " + id + " zostało zdralizowane." +
                "\nStatus został zmnieniony na: " + newStatus.getValue() +
                "\nDziękujemy za zakupy i zapraszamy ponownie" +
                "\nSklep Shop";
    }

    public static String createRefundEmailMessage(Long id, OrderStatus newStatus){
        return "Twoje zamównienie: " + id + " zostało zwrócone." +
                "\nStatus twojego zamównienia został zmnieniony na: " + newStatus.getValue() +
                "\nDziękujemy za zakupy i zapraszamy ponownie" +
                "\nSklep Shop";

    }
}
