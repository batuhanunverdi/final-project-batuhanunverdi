package org.patikadev.finalprojectbatuhanunverdi.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
@Getter
@Setter
public class UserRequest {
    private String email;
    private String name;
    private String tc;
    private String password;
    private Date DOB;
    private String phoneNumber;
}
