package org.example.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;

@Data
@ToString
@Document(collection= "Order")
public class Order {

    @Id
    private int orderId;

    private String productType;

    private String address;

    private String price;
}
