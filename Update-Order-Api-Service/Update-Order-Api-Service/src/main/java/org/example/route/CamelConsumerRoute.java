package org.example.route;

import org.apache.camel.builder.RouteBuilder;
import org.example.model.Order;
import org.example.service.ConsumerProcessor;
import org.springframework.stereotype.Component;

@Component
public class CamelConsumerRoute extends RouteBuilder {

    private final ConsumerProcessor consumerProcessor;

    public CamelConsumerRoute(ConsumerProcessor consumerProcessor) {
        this.consumerProcessor = consumerProcessor;
    }

    @Override
    public void configure() {

        from("activeMQConsumer:order.update.queue")
                .routeId(Order.class.getSimpleName()+"-UpdateMongoDB")
            //  .unmarshal().json(JsonLibrary.Jackson, Order.class)
                .log("************* \n ${body}")
                .process(consumerProcessor)
                .end();
    }
}

