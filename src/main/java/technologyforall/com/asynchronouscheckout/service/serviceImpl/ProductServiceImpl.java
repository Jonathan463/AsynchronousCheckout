package technologyforall.com.asynchronouscheckout.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import technologyforall.com.asynchronouscheckout.model.Product;
import technologyforall.com.asynchronouscheckout.model.dto.ProductRequest;
import technologyforall.com.asynchronouscheckout.model.dto.ProductResponse;
import technologyforall.com.asynchronouscheckout.repository.ProductRepository;
import technologyforall.com.asynchronouscheckout.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setProductSize(productRequest.getProductSize());
        product.setProductQuantity(productRequest.getProductQuantity());
        product.setProductPrice(productRequest.getProductPrice());

        Product savedProduct = productRepository.save(product);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductName(savedProduct.getProductName());
        productResponse.setProductQuantity(savedProduct.getProductQuantity());
        productResponse.setProductSize(savedProduct.getProductSize());
        productResponse.setPrice(savedProduct.getProductPrice());
        return productResponse;
    }

    @Override
    public Product checkIfProductExist(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
