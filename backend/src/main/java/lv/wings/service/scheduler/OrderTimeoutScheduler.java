package lv.wings.service.scheduler;

import java.time.Duration;
import java.time.Instant;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lv.wings.service.OrderService;

@Component
@RequiredArgsConstructor
public class OrderTimeoutScheduler {

    private final TaskScheduler taskScheduler;
    private final OrderService orderService;

    public void scheduleOrderTimeoutCheck(String paymentIntentId, Duration delay) {
        System.out.println("TASK WAS BOOKED FOR SCHEDULER!");
        taskScheduler.schedule(
                () -> orderService.handleOrderTimeout(paymentIntentId),
                Instant.now().plus(delay));
    }
}
