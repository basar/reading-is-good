package com.basarc.readingisgood.domain;

import com.basarc.readingisgood.audit.BaseAuditableEntity;
import com.basarc.readingisgood.domain.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Document("orders")
public class Order extends BaseAuditableEntity {

    @Id
    private String id;

    @DBRef
    private Customer customer;

    @DBRef
    private Book book;

    private int quantity;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal totalPrice;

    private OrderStatus orderStatus;

}
