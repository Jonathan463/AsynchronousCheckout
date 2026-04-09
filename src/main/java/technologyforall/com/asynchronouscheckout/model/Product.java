package technologyforall.com.asynchronouscheckout.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private int productSize;
    private int productQuantity;
    private BigDecimal productPrice;
}
