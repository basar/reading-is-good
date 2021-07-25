package com.basarc.readingisgood.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AuthenticationRequestDto implements Serializable {

    @NotNull
    private String username;
    private String passo;

}
