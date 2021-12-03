package com.sr.command.saga;

import com.sr.CommonService.commands.CompleteOrderCommand;
import com.sr.CommonService.commands.ShipOrderCommand;
import com.sr.CommonService.commands.ValidatePaymentCommand;
import com.sr.CommonService.events.OrderCompletedEvent;
import com.sr.CommonService.events.OrderShipedEvent;
import com.sr.CommonService.events.PaymentProcessedEvent;
import com.sr.CommonService.model.User;
import com.sr.CommonService.queries.GetUserDetailsQueries;
import com.sr.command.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;



    public OrderProcessingSaga() {
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event){

        log.info("Order created event in saga for order id:  ", event.getOrderId());

        GetUserDetailsQueries getUserDetailsQueries = new GetUserDetailsQueries(event.getUserId());

        User user = null;
        try {
            user = queryGateway.query(getUserDetailsQueries, ResponseTypes.instanceOf(User.class)).join();
        }catch (Exception e){
            log.error(e.getMessage());
        }


        ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                .cardDetails(user.getCardDetails())
                .orderId(event.getOrderId())
                .paymentId(UUID.randomUUID().toString())
                .build();

        commandGateway.sendAndWait(validatePaymentCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(PaymentProcessedEvent event){

        log.info("PaymentProcessedEvent processed event in saga for order id:  ", event.getOrderId());
        try {
            ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
                    .shipmentId(UUID.randomUUID().toString())
                    .orderId(event.getOrderId())
                    .build();

            commandGateway.send(shipOrderCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            // Start the conpenseting transaction
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShipedEvent event){

        log.info("OrderShipedEvent processed event in saga for order id:  ", event.getOrderId());

        CompleteOrderCommand completeOrderCommand = CompleteOrderCommand.builder()
                .orderId(event.getOrderId())
                .orderStatus("Approved")
                .build();

        commandGateway.send(completeOrderCommand);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCompletedEvent event){

        log.info("OrderCompletedEvent processed event in saga for order id:  ", event.getOrderId());

    }
}
