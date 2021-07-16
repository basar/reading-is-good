package com.basarc.readingisgood.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCustomerResponse {

    private String id;
    private String name;
    private String surname;
    private String email;
    private String address;

}
