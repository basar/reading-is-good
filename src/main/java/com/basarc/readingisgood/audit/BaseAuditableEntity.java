package com.basarc.readingisgood.audit;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;


public class BaseAuditableEntity implements Serializable {

    @Getter
    @Setter
    @CreatedDate
    private LocalDateTime createdDate;

    @Getter
    @Setter
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Getter
    @Setter
    @CreatedBy
    private String createdByUser;


    @Getter
    @Setter
    @LastModifiedBy
    private String modifiedByUser;

}
