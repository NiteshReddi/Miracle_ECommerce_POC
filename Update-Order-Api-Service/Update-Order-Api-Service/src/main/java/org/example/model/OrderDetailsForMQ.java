package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class OrderDetailsForMQ {

    private int orderId;

@JsonIgnore
    private String address;

    @JsonIgnore
    private String productType;

    @JsonIgnore
    private String price;

}