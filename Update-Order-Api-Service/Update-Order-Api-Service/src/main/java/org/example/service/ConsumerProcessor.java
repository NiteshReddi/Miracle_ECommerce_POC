package org.example.service;

import com.google.gson.Gson;
import com.mongodb.client.result.UpdateResult;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ConsumerProcessor implements Processor {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private OrderRepository orderRepository;

    public ConsumerProcessor(MongoTemplate mongoTemplate, OrderRepository orderRepository) {
        this.mongoTemplate = mongoTemplate;
        this.orderRepository = orderRepository;
    }


    @Override
    public void process(Exchange exchange) throws Exception {

        String order1 = exchange.getIn().getBody(String.class);

        Gson gson = new Gson();
        Order updateOrder = gson.fromJson(order1, Order.class);

        Query query = new Query(Criteria.where("orderId").is(updateOrder.getOrderId()));

        Order fetchOrderDetails = orderRepository.findByOrderId(updateOrder.getOrderId());

        Update update = new Update();
        if (!StringUtils.isEmpty(updateOrder.getPrice())){
            update.set("price", updateOrder.getPrice());
        } else {
            update.set("price", fetchOrderDetails.getPrice());
        }
        if (!StringUtils.isEmpty(updateOrder.getProductType())) {
            update.set("productType", updateOrder.getProductType());
        } else {
            update.set("productType", fetchOrderDetails.getProductType());
        }
        if (!StringUtils.isEmpty(updateOrder.getAddress())){
            update.set("address", updateOrder.getAddress());
        } else {
            update.set("address", fetchOrderDetails.getAddress());
        }

     UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Order.class);

        if (updateResult.getModifiedCount() > 0) {
            System.out.println("Order Updated Successfully with OrderID :" +updateOrder.getOrderId());
        } else {
            throw new RuntimeException("Failed to update order");
        }
    }
}
