package technologyforall.com.asynchronouscheckout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "product")
@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private int productSize;
    private BigDecimal price;
}
