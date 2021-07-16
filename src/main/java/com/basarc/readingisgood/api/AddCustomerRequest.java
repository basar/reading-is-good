package com.basarc.readingisgood.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AddCustomerRequest implements Serializable {

    @NotBlank(message = ApiResponseMessage.RC_CUSTOMER_NAME_PARAM_MISSING)
    private String name;
    private String surname;
    private String address;
    private String email;
}
