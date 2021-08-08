package com.basarc.readingisgood.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageableListDto<T> implements Serializable {

    private long totalCount;
    private List<T> rows;

}
