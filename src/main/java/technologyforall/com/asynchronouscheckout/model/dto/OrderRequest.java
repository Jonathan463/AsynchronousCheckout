package technologyforall.com.asynchronouscheckout.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import technologyforall.com.asynchronouscheckout.model.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

private Long productId;
private int numberOfItem;
}
