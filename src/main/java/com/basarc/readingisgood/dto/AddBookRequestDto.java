package com.basarc.readingisgood.dto;

import com.basarc.readingisgood.api.ApiResponseMessage;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AddBookRequestDto implements Serializable {

    @NotNull(message = ApiResponseMessage.BOOK_NAME_PARAM_MISSING)
    @Size(max = 250, message = ApiResponseMessage.BOOK_NAME_INVALID)
    private String name;

    @NotNull(message = ApiResponseMessage.BOOK_AUTHOR_PARAM_MISSING)
    @Size(max = 100, message = ApiResponseMessage.BOOK_AUTHOR_INVALID)
    private String author;

    @NotNull(message = ApiResponseMessage.BOOK_PRICE_MISSING)
    @DecimalMin(value = "0.0", message = ApiResponseMessage.BOOK_PRICE_INVALID)
    @Digits(integer = 10, fraction = 2, message = ApiResponseMessage.BOOK_PRICE_INVALID)
    private BigDecimal price;

    @NotNull(message = ApiResponseMessage.BOOK_INITIAL_STOCK_MISSING)
    @Min(value = 0, message = ApiResponseMessage.BOOK_INITIAL_STOCK_INVALID)
    private Long initialStock;


}
