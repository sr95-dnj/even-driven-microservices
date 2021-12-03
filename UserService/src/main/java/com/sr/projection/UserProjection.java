package com.sr.projection;

import com.sr.CommonService.model.CardDetails;
import com.sr.CommonService.model.User;
import com.sr.CommonService.queries.GetUserDetailsQueries;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    @QueryHandler
    public User getUserPaymentDetails(GetUserDetailsQueries queries){
        CardDetails cardDetails = CardDetails.builder()
                .name("Siddiquer Rahman")
                .validUntilMonth(03)
                .validUntilYear(2022)
                .cardNumber("123456789")
                .cvv(121)
                .build();
        return User.builder()
                .userId(queries.getUserId())
                .firstName("Siddiquer")
                .lastname("Rahman")
                .cardDetails(cardDetails)
                .build();
    }
}
