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
import technologyforall.com.asynchronouscheckout.model.dto.ProductRequest;
import technologyforall.com.asynchronouscheckout.repository.OrderRepository;
import technologyforall.com.asynchronouscheckout.repository.ProductRepository;
import technologyforall.com.asynchronouscheckout.service.OrderService;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

   final ProductRepository productRepository;
   final OrderRepository orderRepository;

   @Transactional
    @Override
    public OrderResponse orderProduct(OrderRequest orderRequest) throws InterruptedException {
        Optional<Product> optionalProduct = productRepository.findById(orderRequest.getProductId());


        if(optionalProduct.isPresent()){


            Product product = optionalProduct.get();

            if(product.getProductQuantity() > 0) {

                int currentQuantity = product.getProductQuantity();

                log.info("THREAD {} - READ quantity {}", Thread.currentThread().getName(), currentQuantity);

                Thread.sleep(500);
                int newQuantity = currentQuantity - orderRequest.getNumberOfItem();

                product.setProductQuantity(newQuantity);

                log.info("THREAD {} - THREAD quantity {}", Thread.currentThread().getName(), newQuantity);

                productRepository.save(product);


                Order order = new Order();
                order.setProduct(product);
                order.setNumberOfItem(orderRequest.getNumberOfItem());

                Order savedOrder = orderRepository.save(order);
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setProduct(savedOrder.getProduct());
                orderResponse.setNumberOfItem(savedOrder.getNumberOfItem());
                return orderResponse;
            }
        }
        throw new ResourceNotFoundException("Order","id",orderRequest.getProductId());
    }
}
