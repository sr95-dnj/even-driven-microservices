package com.sr.CommonService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String userId;
    private String firstName;
    private String lastname;
    private CardDetails cardDetails;
}
