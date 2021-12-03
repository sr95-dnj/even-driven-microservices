package com.sr.command.events;

import lombok.Data;

@Data
public class OrderCreatedEvent {

    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private String orderStatus;
}
