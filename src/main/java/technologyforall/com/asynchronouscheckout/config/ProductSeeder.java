package technologyforall.com.asynchronouscheckout.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import technologyforall.com.asynchronouscheckout.model.Product;
import technologyforall.com.asynchronouscheckout.repository.ProductRepository;
import technologyforall.com.asynchronouscheckout.service.ProductService;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Component
public class ProductSeeder implements CommandLineRunner {

    final ProductRepository productRepository;
    final ProductService productService;

    @Override
    public void run(String... args) throws Exception {


        try{
            Product product = productService.checkIfProductExist(1L);

            if(product != null){
                return;
            }

            Product seedProduct = new Product(1L,"Water",10,1,new BigDecimal("500.0"));

            productRepository.save(seedProduct);

        }catch (Exception e){
            log.debug(e.getMessage());
        }
    }
}
