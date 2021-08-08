package com.basarc.readingisgood.dto;

import com.basarc.readingisgood.api.ApiResponseMessage;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateBookStockRequestDto implements Serializable {

    @NotNull(message = ApiResponseMessage.BOOK_ID_MISSING)
    private String bookId;

    @NotNull(message = ApiResponseMessage.STOCK_AMOUNT_MISSING)
    @Min(value = 1,message = ApiResponseMessage.STOCK_AMOUNT_INVALID)
    private Long stockAmount;

}
