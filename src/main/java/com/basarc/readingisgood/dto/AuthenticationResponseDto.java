package com.basarc.readingisgood.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AuthenticationResponseDto implements Serializable {

    private String jwtToken;

}
