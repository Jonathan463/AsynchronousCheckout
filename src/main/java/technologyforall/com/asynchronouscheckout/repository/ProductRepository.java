package technologyforall.com.asynchronouscheckout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technologyforall.com.asynchronouscheckout.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
