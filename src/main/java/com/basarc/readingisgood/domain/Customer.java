package com.basarc.readingisgood.domain;

import com.basarc.readingisgood.audit.BaseAuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Document("customers")
public class Customer extends BaseAuditableEntity {

    @Id
    private String id;

    private String name;

    private String surname;

    @Indexed(unique = true)
    private String email;

    private String address;

}
