package org.patikadev.finalprojectbatuhanunverdi.validator;

import org.patikadev.finalprojectbatuhanunverdi.model.request.AuthenticationRequest;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author Mert Batuhan UNVERDI
 * @since 17.05.2022
 */
public class AuthenticationRequestValidator {

    public void validate(AuthenticationRequest authenticationRequest) {
        if (Objects.isNull(authenticationRequest)) {
            throw new IllegalArgumentException("AuthenticationRequest can't be null");
        }
        if (!(StringUtils.hasLength(authenticationRequest.getTc()))) {
            throw new IllegalArgumentException("TC can't be null");
        }
        if (!(StringUtils.hasLength(authenticationRequest.getPassword()))) {
            throw new IllegalArgumentException("Password can't be null");
        }
    }
}
