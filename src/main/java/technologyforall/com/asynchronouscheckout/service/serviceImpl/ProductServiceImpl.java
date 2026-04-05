package technologyforall.com.asynchronouscheckout.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import technologyforall.com.asynchronouscheckout.model.Product;
import technologyforall.com.asynchronouscheckout.model.dto.ProductRequest;
import technologyforall.com.asynchronouscheckout.model.dto.ProductResponse;
import technologyforall.com.asynchronouscheckout.repository.ProductRepository;
import technologyforall.com.asynchronouscheckout.service.ProductService;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = new Product();
        product.setProductName(product.getProductName());
        product.setProductSize(productRequest.getProductSize());
        product.setProductSize(product.getProductSize());

        Product savedProduct = productRepository.save(product);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductName(savedProduct.getProductName());
        productResponse.setProductSize(savedProduct.getProductSize());
        productResponse.setProductSize(savedProduct.getProductSize());
        return productResponse;
    }
}
