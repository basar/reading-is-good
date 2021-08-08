package com.basarc.readingisgood.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AddBookResponseDto implements Serializable {

    private String id;
    private String name;
    private String author;
    private BigDecimal price;
    private long stock;

}
