package com.sr.controller;

import com.sr.CommonService.model.User;
import com.sr.CommonService.queries.GetUserDetailsQueries;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private transient QueryGateway queryGateway;



    @GetMapping("/{userId}")
    public User getUserPaymentDetails(@PathVariable String userId){

        GetUserDetailsQueries getUserDetailsQueries = new GetUserDetailsQueries(userId);

        User user = queryGateway.query(getUserDetailsQueries, ResponseTypes.instanceOf(User.class)).join();
        return user;
    }
}
