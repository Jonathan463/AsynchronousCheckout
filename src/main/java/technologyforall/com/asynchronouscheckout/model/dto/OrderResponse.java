package technologyforall.com.asynchronouscheckout.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import technologyforall.com.asynchronouscheckout.model.Product;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Product product;
    private int numberOfItem;
}
