package org.patikadev.finalprojectbatuhanunverdi.converter;

import org.patikadev.finalprojectbatuhanunverdi.entity.User;
import org.patikadev.finalprojectbatuhanunverdi.model.request.UserRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
@Component
public class UserConverter {

    public User toUser(UserRequest userRequest) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setTc(userRequest.getTc());
        user.setDOB(userRequest.getDOB());
        user.setName(userRequest.getName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        return user;
    }
}
