package com.basarc.readingisgood.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class UpdateBookStockResponseDto implements Serializable {

    private String bookId;
    private long totalStock;

    public UpdateBookStockResponseDto(String bookId, long totalStock) {
        this.bookId = bookId;
        this.totalStock = totalStock;
    }
}
