package com.sr.command.aggregate.aggregate;

import com.sr.CommonService.commands.ShipOrderCommand;
import com.sr.CommonService.events.OrderShipedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class ShipmentAggregate {

    @AggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;

    public ShipmentAggregate() {
    }


    @CommandHandler
    public ShipmentAggregate(ShipOrderCommand shipOrderCommand) {
        // validate this command
        // publish this order

        OrderShipedEvent orderShipedEvent = OrderShipedEvent.builder()
                .shipmentId(shipOrderCommand.getShipmentId())
                .orderId(shipOrderCommand.getOrderId())
                .shipmentStatus("Shipped")
                .build();

        AggregateLifecycle.apply(orderShipedEvent);


    }

    @EventSourcingHandler
    public void on(OrderShipedEvent event){

        this.orderId = event.getOrderId();
        this.shipmentId = event.getShipmentId();
        this.shipmentStatus = event.getShipmentStatus();
    }
}
