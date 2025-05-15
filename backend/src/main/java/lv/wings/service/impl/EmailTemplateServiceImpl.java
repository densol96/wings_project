package lv.wings.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import lombok.RequiredArgsConstructor;
import lv.wings.enums.LocaleCode;
import lv.wings.model.entity.Customer;
import lv.wings.model.entity.Order;
import lv.wings.model.entity.OrderItem;
import lv.wings.model.security.User;
import lv.wings.service.DeliveryTypeService;
import lv.wings.service.EmailTemplateService;
import lv.wings.service.LocaleService;
import lv.wings.service.ProductService;

@Service
@RequiredArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final LocaleService localeService;
    private final ProductService productService;
    private final DeliveryTypeService deliveryTypeService;

    public String generateSuccessfulPaymentEmailHtml(Order order) {
        try {
            String template = loadTemplate("/templates/order-success-email-" + order.getLocale().getCode() + ".html");
            LocaleCode orderLocale = order.getLocale();

            String tableRows = generateTableRows(order);
            String total = order.getTotal().toPlainString();

            return template
                    .replace("{{orderId}}", String.valueOf(order.getId()))
                    .replace("{{deliveryName}}", deliveryTypeService.proccessDeliveryMethod(order, orderLocale))
                    .replace("{{discount}}", processDiscount(order))
                    .replace("{{message}}", localeService.getMessageForSpecificLocale("email.success.message", orderLocale))
                    .replace("{{items}}", tableRows)
                    .replace("{{total}}", total);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generateSuccessfulPaymentEmailHtml", e);
        }
    }

    @Override
    public String generateLoginAttemptsExceededEmailHtml(User user, String requestUnlockUrl) {
        try {
            String template = loadTemplate("/templates/account-locked.html");

            return template
                    .replace("{{name}}", user.getFirstName() + " " + user.getLastName())
                    .replace("{{requestUnlockUrl}}", requestUnlockUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generateLoginAttemptsExceededEmailHtml", e);
        }
    }

    @Override
    public String generateUnlockAccountEmailHtml(User user, String unlockUrl) {
        try {
            String template = loadTemplate("/templates/unlock-account.html");

            return template
                    .replace("{{name}}", user.getFirstName() + " " + user.getLastName())
                    .replace("{{unlockUrl}}", unlockUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generateUnlockAccountEmailHtml", e);
        }
    }

    @Override
    public String generateResetPasswordEmailHtml(User user, String resetUrl) {
        try {
            String template = loadTemplate("/templates/reset-password.html");

            return template
                    .replace("{{name}}", user.getFirstName() + " " + user.getLastName())
                    .replace("{{resetUrl}}", resetUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generateResetPasswordEmailHtml", e);
        }
    }

    @Override
    public String generateEmailWasChangedHtml(User user, String oldEmail, String newEmail) {
        try {
            String template = loadTemplate("/templates/email-changed.html");

            return template
                    .replace("{{name}}", user.getFirstName() + " " + user.getLastName())
                    .replace("{{oldEmail}}", oldEmail)
                    .replace("{{newEmail}}", newEmail);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generateEmailWasChangedHtml", e);
        }
    }

    @Override
    public String generatePasswordWasChangedHtml(User user) {
        try {
            String template = loadTemplate("/templates/password-changed.html");
            return template
                    .replace("{{name}}", user.getFirstName() + " " + user.getLastName());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generatePasswordWasChangedHtml", e);
        }
    }

    @Override
    public String generateNewPasswordHtml(User user, String newPassword) {
        // Unlike generatePasswordWasChangedHtml, this method is used when the admin changes the user password.
        try {
            String template = loadTemplate("/templates/new-password.html");
            return template
                    .replace("{{name}}", user.getFirstName() + " " + user.getLastName())
                    .replace("{{newPassword}}", newPassword);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generateNewPasswordHtml", e);
        }
    }

    @Override
    public String generateNewUsernameHtml(User user) {
        try {
            String template = loadTemplate("/templates/new-username.html");
            return template
                    .replace("{{name}}", user.getFirstName() + " " + user.getLastName())
                    .replace("{{newUsername}}", user.getUsername());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generateNewUsernameHtml", e);
        }
    }

    @Override
    public String generateNewEmployeeHtml(User user, String newPassword) {
        try {
            String template = loadTemplate("/templates/new-user.html");
            return template
                    .replace("{{name}}", user.getFirstName() + " " + user.getLastName())
                    .replace("{{newUsername}}", user.getUsername())
                    .replace("{{newPassword}}", newPassword);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generateNewUsernameHtml", e);
        }
    }

    @Override
    public String generateAdminChangedPasswordHtml(User user, String newPassword) {
        try {
            String template = loadTemplate("/templates/admin-changed-password.html");
            return template
                    .replace("{{name}}", user.getFirstName() + " " + user.getLastName())
                    .replace("{{newPassword}}", newPassword);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generateNewUsernameHtml", e);
        }
    }

    @Override
    public String generateOrderClosedHtml(Order order) {
        Customer customer = order.getCustomer();
        try {
            String template = loadTemplate("/templates/order-closed-" + order.getLocale().getCode() + ".html");
            return template
                    .replace("{{name}}", customer.getFirstName() + " " + customer.getLastName())
                    .replace("{{orderId}}", order.getId() + "");
        } catch (Exception e) {
            throw new RuntimeException("Failed to generateNewUsernameHtml", e);
        }
    }

    @Override
    public String generateOrderWasSentHtml(Customer customer, String additionalComment, LocaleCode locale) {
        try {
            String template = loadTemplate("/templates/order-was-sent-" + locale.getCode() + ".html");
            return template
                    .replace("{{name}}", customer.getFirstName() + " " + customer.getLastName())
                    .replace("{{comment}}", additionalComment == null ? "" : additionalComment);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generateNewUsernameHtml", e);
        }
    }

    private String loadTemplate(String path) throws Exception {
        InputStream stream = getClass().getResourceAsStream(path);
        if (stream == null) {
            throw new IllegalArgumentException("Template not found at: " + path);
        }
        return StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
    }

    private String generateTableRows(Order order) {
        StringBuilder builder = new StringBuilder();
        for (OrderItem item : order.getOrderItems()) {

            String productTitle = productService.getSelectedTranslation(item.getProduct(), order.getLocale()).getTitle();
            builder.append("<tr>")
                    .append("<td>").append(productTitle).append("</td>")
                    .append("<td>").append(item.getAmount()).append("</td>")
                    .append("<td>").append(item.getProduct().getPrice()).append("</td>")
                    .append("<td>").append(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getAmount()))).append("</td>")
                    .append("</tr>");
        }
        return builder.toString();
    }

    private String processDiscount(Order order) {
        try {
            String discountTemplate = loadTemplate("/templates/discount-line-" + order.getLocale().getCode() + ".html");
            BigDecimal discount = order.getDiscountAtOrderTime();
            String discountHtmlLine = "";

            if (discount.compareTo(BigDecimal.ZERO) > 0) {
                discountHtmlLine = discountTemplate.replace("{{discountAmount}}", discount.toPlainString());
            }
            return discountHtmlLine;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate email HTML", e);
        }
    }


}
