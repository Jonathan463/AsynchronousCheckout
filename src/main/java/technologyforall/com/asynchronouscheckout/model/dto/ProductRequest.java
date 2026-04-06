package technologyforall.com.asynchronouscheckout.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private int productSize;
    private int productQuantity;
    private String productName;
    private BigDecimal productPrice;
}
