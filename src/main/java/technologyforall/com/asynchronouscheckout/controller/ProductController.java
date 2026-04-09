package technologyforall.com.asynchronouscheckout.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technologyforall.com.asynchronouscheckout.model.Product;
import technologyforall.com.asynchronouscheckout.model.dto.ProductRequest;
import technologyforall.com.asynchronouscheckout.model.dto.ProductResponse;
import technologyforall.com.asynchronouscheckout.service.ProductService;

@RestController
@Slf4j
@RequestMapping("/product/v1")
@RequiredArgsConstructor
public class ProductController {

    final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        Product product = productService.checkIfProductExist(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(product);
    }

}
