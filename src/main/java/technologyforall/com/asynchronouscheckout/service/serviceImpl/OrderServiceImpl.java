package technologyforall.com.asynchronouscheckout.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import technologyforall.com.asynchronouscheckout.exception.ResourceNotFoundException;
import technologyforall.com.asynchronouscheckout.model.Order;
import technologyforall.com.asynchronouscheckout.model.Product;
import technologyforall.com.asynchronouscheckout.model.dto.OrderRequest;
import technologyforall.com.asynchronouscheckout.model.dto.OrderResponse;
import technologyforall.com.asynchronouscheckout.repository.OrderRepository;
import technologyforall.com.asynchronouscheckout.repository.ProductRepository;
import technologyforall.com.asynchronouscheckout.service.OrderService;



@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

   final ProductRepository productRepository;
   final OrderRepository orderRepository;

   @Transactional
    @Override
    public OrderResponse orderProduct(OrderRequest orderRequest) throws InterruptedException {
        int zeroOrOne = productRepository.atomicUpdate(orderRequest.getProductId());


        if(zeroOrOne == 1){

                Product fetchedProduct = productRepository.findProductById(orderRequest.getProductId());

                Order order = new Order();
                order.setProduct(fetchedProduct);
                order.setNumberOfItem(orderRequest.getNumberOfItem());

                Order savedOrder = orderRepository.save(order);
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setProduct(savedOrder.getProduct());
                orderResponse.setNumberOfItem(savedOrder.getNumberOfItem());
                return orderResponse;

        }
        throw new ResourceNotFoundException("Order","id",orderRequest.getProductId());
    }
}
