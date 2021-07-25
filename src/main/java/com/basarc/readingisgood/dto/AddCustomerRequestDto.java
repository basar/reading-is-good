package com.basarc.readingisgood.dto;

import com.basarc.readingisgood.api.ApiResponseMessage;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class AddCustomerRequestDto implements Serializable {

    @NotNull(message = ApiResponseMessage.CUSTOMER_NAME_PARAM_MISSING)
    @Size(min = 2, max = 50,message = ApiResponseMessage.INVALID_NAME)
    private String name;

    @NotNull(message = ApiResponseMessage.CUSTOMER_SURNAME_PARAM_MISSING)
    @Size(min = 2, max = 50,message = ApiResponseMessage.INVALID_SURNAME)
    private String surname;

    @NotNull(message = ApiResponseMessage.CUSTOMER_ADRESS_PARAM_MISSING)
    @Size(min = 2, max = 250,message = ApiResponseMessage.INVALID_ADDRESS)
    private String address;

    @NotNull(message = ApiResponseMessage.CUSTOMER_EMAIL_PARAM_MISSING)
    @Email(message = ApiResponseMessage.INVALID_EMAIL)
    private String email;
}
