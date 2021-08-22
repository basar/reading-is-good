package com.basarc.readingisgood.controller;

import com.basarc.readingisgood.api.ApiConstant;
import com.basarc.readingisgood.api.ApiResponse;
import com.basarc.readingisgood.dto.AuthenticationRequestDto;
import com.basarc.readingisgood.dto.AuthenticationResponseDto;
import com.basarc.readingisgood.service.interfaces.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthenticationController extends AbstractApiController {

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public AuthenticationController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping(ApiConstant.Path.AUTHENTICATE)
    @ResponseBody
    public ResponseEntity<ApiResponse<AuthenticationResponseDto>> doAuthenticate(
            @RequestBody @Valid AuthenticationRequestDto request) {
        return ok(userAuthenticationService.doAuthentication(request));
    }


}
