package com.basarc.readingisgood.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AddBookRequestDto implements Serializable {


    private String name;

    private String author;

    private BigDecimal price;

    private Long initialStock;


}
