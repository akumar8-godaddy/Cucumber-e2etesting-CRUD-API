package Order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class OrderEntity {

    private String orderID;
    private String customerID;
    private String productID;
    private String customerName;
    private String customerAddress;
    private Integer subtotal;

}
