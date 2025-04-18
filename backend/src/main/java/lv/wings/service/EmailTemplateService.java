package lv.wings.service;

import lv.wings.model.entity.Order;

public interface EmailTemplateService {
    String generateSuccessfulPaymentEmailHtml(Order order);
}
