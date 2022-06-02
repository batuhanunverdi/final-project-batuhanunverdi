package org.patikadev.finalprojectbatuhanunverdi.repository;

import org.patikadev.finalprojectbatuhanunverdi.entity.Account;
import org.patikadev.finalprojectbatuhanunverdi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByIBAN(String iBan);

    Collection<Account> findByUser(User user);


}
