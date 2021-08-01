package com.basarc.readingisgood.domain;

import com.basarc.readingisgood.audit.BaseAuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Document("books")
public class Book extends BaseAuditableEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String author;

    private BigDecimal price;

    private long stock;


}
