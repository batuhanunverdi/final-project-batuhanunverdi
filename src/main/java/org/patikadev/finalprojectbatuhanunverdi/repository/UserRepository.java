package org.patikadev.finalprojectbatuhanunverdi.repository;

import org.patikadev.finalprojectbatuhanunverdi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Mert Batuhan UNVERDI
 * @since 16.05.2022
 */
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByTc(String tc);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);

}
