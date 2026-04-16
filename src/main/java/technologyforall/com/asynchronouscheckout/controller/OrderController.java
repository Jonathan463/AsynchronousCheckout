package technologyforall.com.asynchronouscheckout.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import technologyforall.com.asynchronouscheckout.model.dto.OrderRequest;
import technologyforall.com.asynchronouscheckout.model.dto.OrderResponse;
import technologyforall.com.asynchronouscheckout.service.OrderService;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/order/v1")
@RequiredArgsConstructor
public class OrderController {

    final OrderService orderService;
    final KafkaTemplate<String, OrderRequest> kafkaTemplate;

    @Value("${app.kafka.topic.order}")
    private String orderTopic;

    @PostMapping
    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderRequest orderRequest) throws InterruptedException{
        //OrderResponse orderResponse = orderService.orderProduct(orderRequest);

        String key = orderRequest.getProductId().toString();

        kafkaTemplate.send(orderTopic, key,orderRequest);

        log.info("Order request sent to Kafka. productId={}, quantity={}",
                orderRequest.getProductId(),
                orderRequest.getNumberOfItem());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                Map.of("Message", "Order accepted",
                        "ProductId", orderRequest.getProductId()));


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id){
        OrderResponse orderResponse = orderService.findOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

}
