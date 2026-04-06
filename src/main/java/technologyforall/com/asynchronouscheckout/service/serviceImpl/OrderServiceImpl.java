package technologyforall.com.asynchronouscheckout.service.serviceImpl;

import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    ProductRepository productRepository;
    OrderRepository orderRepository;

    @Override
    public OrderResponse orderProduct(OrderRequest orderRequest) {
        Optional<Product> optionalProduct = productRepository.findById(orderRequest.getProductId());


        if(optionalProduct.isPresent()){

            Product product = optionalProduct.get();

            Order order = new Order();
            order.setProduct(product);
            order.setNumberOfItem(orderRequest.getNumberOfItem());

            Order savedOrder = orderRepository.save(order);
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setProduct(savedOrder.getProduct());
            orderResponse.setNumberOfItem(savedOrder.getNumberOfItem());
            return orderResponse;
        }
        throw new NoSuchElementException("Such Element Does not exist");
    }
}
