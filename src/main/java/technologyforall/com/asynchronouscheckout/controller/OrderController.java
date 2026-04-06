package technologyforall.com.asynchronouscheckout.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technologyforall.com.asynchronouscheckout.model.dto.OrderRequest;
import technologyforall.com.asynchronouscheckout.model.dto.OrderResponse;
import technologyforall.com.asynchronouscheckout.service.OrderService;

@RestController
@Slf4j
@RequestMapping("/order/v1")
@RequiredArgsConstructor
public class OrderController {

    OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = orderService.orderProduct(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

}
