package org.patikadev.finalprojectbatuhanunverdi.service;

import org.patikadev.finalprojectbatuhanunverdi.entity.User;
import org.patikadev.finalprojectbatuhanunverdi.model.request.UserRequest;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
public interface UserService {
    void addUser(UserRequest userRequest);

    void updateUser(Long id,UserRequest userRequest);

    void deleteUser(Long id);

    User getUserByTc(String tc);
}
