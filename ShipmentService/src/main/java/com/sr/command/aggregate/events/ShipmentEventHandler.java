package com.sr.command.aggregate.events;

import com.sr.CommonService.events.OrderShipedEvent;
import com.sr.command.aggregate.data.Shipment;
import com.sr.command.aggregate.data.ShipmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipmentEventHandler {

    @Autowired
    private ShipmentRepository shipmentRepository;


    public void on(OrderShipedEvent event){

        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(event, shipment);
        shipmentRepository.save(shipment);
    }
}
