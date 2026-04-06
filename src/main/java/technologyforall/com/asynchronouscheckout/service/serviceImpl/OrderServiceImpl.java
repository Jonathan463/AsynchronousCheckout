package technologyforall.com.asynchronouscheckout.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

   final ProductRepository productRepository;
   final OrderRepository orderRepository;

   @Transactional
    @Override
    public OrderResponse orderProduct(OrderRequest orderRequest) {
        Optional<Product> optionalProduct = productRepository.findById(orderRequest.getProductId());


        if(optionalProduct.isPresent()){


            Product product = optionalProduct.get();

            if(product.getProductQuantity() > 0) {

                int newQuantity = product.getProductQuantity() - orderRequest.getNumberOfItem();
                product.setProductQuantity(newQuantity);

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
        throw new NoSuchElementException("Such Element Does not exist");
    }
}
