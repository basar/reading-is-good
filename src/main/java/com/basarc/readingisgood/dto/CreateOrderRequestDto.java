package com.basarc.readingisgood.dto;

import com.basarc.readingisgood.api.ApiResponseMessage;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class CreateOrderRequestDto implements Serializable {

    @NotNull
    private String customerId;

    @NotNull
    private String bookId;

    @NotNull
    @Min(value = 1,message = ApiResponseMessage.INVALID_QUANTITY)
    private Integer quantity;

}
