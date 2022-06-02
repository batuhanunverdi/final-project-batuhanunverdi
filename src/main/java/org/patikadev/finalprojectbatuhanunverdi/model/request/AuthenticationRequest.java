package org.patikadev.finalprojectbatuhanunverdi.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mert Batuhan UNVERDI
 * @since 17.05.2022
 */
@Getter
@Setter
public class AuthenticationRequest {
    private String tc;
    private String password;

}
