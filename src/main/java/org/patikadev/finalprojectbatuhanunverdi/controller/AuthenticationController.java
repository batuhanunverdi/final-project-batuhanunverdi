package org.patikadev.finalprojectbatuhanunverdi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.helper.JWTHelper;
import org.patikadev.finalprojectbatuhanunverdi.model.request.AuthenticationRequest;
import org.patikadev.finalprojectbatuhanunverdi.model.request.UserRequest;
import org.patikadev.finalprojectbatuhanunverdi.model.response.AuthenticationResponse;
import org.patikadev.finalprojectbatuhanunverdi.service.UserService;
import org.patikadev.finalprojectbatuhanunverdi.validator.AuthenticationRequestValidator;
import org.patikadev.finalprojectbatuhanunverdi.validator.UserRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mert Batuhan UNVERDI
 * @since 17.05.2022
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Api(value = "User Sign-in Sign-up Documentation")
public class AuthenticationController {
    private final UserRequestValidator userRequestValidator;

    private final AuthenticationRequestValidator authenticationRequestValidator;
    private final AuthenticationManager authenticationManager;
    private final JWTHelper jwtHelper;
    private final UserService userService;


    @PostMapping(path = "/sign-in")
    @ApiOperation(value = "New User adding method")
    public ResponseEntity<?> signIn(@RequestBody AuthenticationRequest authenticationRequest) {
        authenticationRequestValidator.validate(authenticationRequest);
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(authenticationRequest.getTc(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtHelper.generate(authenticationRequest.getTc());
        return ResponseEntity.ok(new AuthenticationResponse(token,authentication.getName()));
    }

    @PostMapping(path = "/sign-up")
    @ApiOperation(value = "User Login method")
    public ResponseEntity<?> signUp(@RequestBody UserRequest userRequest){
        userRequestValidator.validate(userRequest);
        userService.addUser(userRequest);
        return ResponseEntity.ok().build();
    }


}
