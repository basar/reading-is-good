package com.basarc.readingisgood.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AddCustomerResponseDto implements Serializable {

    private String id;
    private String name;
    private String surname;
    private String email;
    private String address;

}
