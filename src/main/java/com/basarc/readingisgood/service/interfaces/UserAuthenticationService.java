package com.basarc.readingisgood.service.interfaces;

import com.basarc.readingisgood.dto.AuthenticationRequestDto;
import com.basarc.readingisgood.dto.AuthenticationResponseDto;

public interface UserAuthenticationService {

    AuthenticationResponseDto doAuthentication(AuthenticationRequestDto authRequest);

}
