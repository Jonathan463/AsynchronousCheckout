package technologyforall.com.asynchronouscheckout.service.serviceImpl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import technologyforall.com.asynchronouscheckout.model.dto.OrderRequest;
import technologyforall.com.asynchronouscheckout.model.dto.OrderResponse;


@Component
public class OrderConsumer {

   final OrderServiceImpl orderService;

    public OrderConsumer(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "order-topic", groupId = "order-group")
    OrderResponse orderConsumer(OrderRequest orderRequest) throws InterruptedException {
       return orderService.orderProduct(orderRequest);
    }
}
