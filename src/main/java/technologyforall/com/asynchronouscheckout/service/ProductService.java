package technologyforall.com.asynchronouscheckout.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import technologyforall.com.asynchronouscheckout.model.Product;
import technologyforall.com.asynchronouscheckout.model.dto.ProductRequest;
import technologyforall.com.asynchronouscheckout.model.dto.ProductResponse;


public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);
    Product checkIfProductExist(Long id);

}
