package technologyforall.com.asynchronouscheckout.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technologyforall.com.asynchronouscheckout.model.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Modifying
    @Query("UPDATE Product SET productQuantity = productQuantity - 1 WHERE id = :id AND productQuantity > 0 ")
    int atomicUpdate(@Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findProductById(@Param("id") Long id);
}
