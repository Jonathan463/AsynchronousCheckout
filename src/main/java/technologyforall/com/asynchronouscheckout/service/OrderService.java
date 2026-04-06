package technologyforall.com.asynchronouscheckout.service;

import org.springframework.stereotype.Service;
import technologyforall.com.asynchronouscheckout.model.dto.OrderRequest;
import technologyforall.com.asynchronouscheckout.model.dto.OrderResponse;
import technologyforall.com.asynchronouscheckout.model.dto.ProductRequest;

@Service
public interface OrderService {

    OrderResponse orderProduct(OrderRequest orderRequest);
}
