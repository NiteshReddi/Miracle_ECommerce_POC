package org.example.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.example.model.Order;
import org.springframework.stereotype.Component;

@Component
public class CamelProducerRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from("direct:UpdateOrderDetails")
                .routeId(Order.class.getSimpleName()+"-SendToMQ")
                 .marshal().json(JsonLibrary.Jackson, Order.class)
                .to("activeMQProducer:order.update.queue")
                .end();
    }
}

