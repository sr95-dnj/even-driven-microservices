package com.sr.CommonService.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderShipedEvent {


    private String shipmentId;
    private String orderId;
    private String shipmentStatus;
}
