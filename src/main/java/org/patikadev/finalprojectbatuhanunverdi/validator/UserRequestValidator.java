package org.patikadev.finalprojectbatuhanunverdi.validator;


import org.patikadev.finalprojectbatuhanunverdi.exception.ValidationOperationException;
import org.patikadev.finalprojectbatuhanunverdi.model.request.UserRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.Objects;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
@Component
public class UserRequestValidator {
    public void validate(UserRequest userRequest){
        if(Objects.isNull(userRequest)){
            throw new ValidationOperationException.UserNotValidException("User can not be null or empty");
        }
        if(!(StringUtils.hasLength(userRequest.getName()))){
            throw new ValidationOperationException.UserNotValidException("Name can not be null or empty");
        }
        if(!(StringUtils.hasLength(userRequest.getEmail()))){
            throw new ValidationOperationException.UserNotValidException("Email can not be null or empty");
        }
        if(!(StringUtils.hasLength(userRequest.getTc()))){
            throw new ValidationOperationException.UserNotValidException("TC can not be null or empty");
        }
        if(!(StringUtils.hasLength(userRequest.getPassword()))){
            throw new ValidationOperationException.UserNotValidException("Password can not be null or empty");
        }
        if(!(StringUtils.hasLength(userRequest.getPhoneNumber()))){
            throw new ValidationOperationException.UserNotValidException("Phone Number can not be null or empty");
        }
        if(userRequest.getDOB()==null){
            throw new ValidationOperationException.UserNotValidException("Date of Birth can not be null or empty");
        }
        if(!userRequest.getName().matches("^[A-Za-z][A-Za-z0-9_ ]{7,29}$")){
            throw new ValidationOperationException.UserNotValidException("User name is not correct");

        }
        if(!userRequest.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
        {
            throw new ValidationOperationException.UserNotValidException("Customer email is not correct");
        }
        if(!userRequest.getPassword().matches("^[A-Za-z][A-Za-z0-9_]{7,29}$")){
            throw new ValidationOperationException.UserNotValidException("Customer password is not correct");
        }
    }
}
