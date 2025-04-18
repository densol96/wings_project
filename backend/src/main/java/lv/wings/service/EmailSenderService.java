package lv.wings.service;

import lv.wings.model.entity.Order;

public interface EmailSenderService {
    void sendSimpleEmail(String to, String subject, String text);

    void sendOrderSuccessEmail(Order order);
}
