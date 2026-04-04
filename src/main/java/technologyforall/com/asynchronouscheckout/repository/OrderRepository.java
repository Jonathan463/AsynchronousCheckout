package technologyforall.com.asynchronouscheckout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technologyforall.com.asynchronouscheckout.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
