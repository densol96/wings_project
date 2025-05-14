package lv.wings.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.annotation.AllowedSortFields;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.orders.OrderAdminDto;
import lv.wings.dto.response.admin.orders.OrderFullAdminDto;
import lv.wings.enums.Country;
import lv.wings.enums.DeliveryMethod;
import lv.wings.enums.OrderStatus;
import lv.wings.service.OrderService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/orders")
@RequiredArgsConstructor
public class OrderAdminController {

    private final OrderService orderService;

    @GetMapping
    @AllowedSortFields({"createdAt", "lastModifiedAt"})
    public ResponseEntity<Page<OrderAdminDto>> getOrders(
            @PageableDefault(page = 1, size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String q,
            @RequestParam(name = "status", required = false) OrderStatus orderStatus,
            @RequestParam(name = "country", defaultValue = "LV") Country toCountry,
            @RequestParam(name = "deliveryMethod", required = false) DeliveryMethod deliveryMethod) {
        log.info("Received GET request on /api/v1/admin/orders");
        System.out.println("Delivery method ==> " + deliveryMethod);
        return ResponseEntity.ok(orderService.searchOrders(pageable, q, orderStatus, toCountry, deliveryMethod));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderFullAdminDto> getOrder(@PathVariable Integer id) {
        log.info("Received GET request on /api/v1/admin/orders/{}", id);
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PatchMapping("/{id}/upgrade")
    public ResponseEntity<BasicMessageDto> promoteOrder(@PathVariable Integer id) {
        log.info("Received PATCH request on /api/v1/admin/orders/{}/upgrade", id);
        return ResponseEntity.ok(orderService.upgradeOrder(id));
    }


}
