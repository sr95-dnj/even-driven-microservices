package com.sr.command.aggregate.events;

import com.sr.CommonService.events.PaymentProcessedEvent;
import com.sr.command.aggregate.data.Payment;
import com.sr.command.aggregate.data.PaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentEventsHandler {

    @Autowired
    private PaymentRepository paymentRepository;


    @EventHandler
    public void on(PaymentProcessedEvent event){
        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentStatus("Completed")
                .timeStamp(new Date())
                .build();
    }
}
