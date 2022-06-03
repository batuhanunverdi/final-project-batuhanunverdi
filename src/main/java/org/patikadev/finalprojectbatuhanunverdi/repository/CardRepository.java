package org.patikadev.finalprojectbatuhanunverdi.repository;

import org.patikadev.finalprojectbatuhanunverdi.entity.Account;
import org.patikadev.finalprojectbatuhanunverdi.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Mert Batuhan UNVERDI
 * @since 2.06.2022
 */
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardNumber(String cardNumber);

    Optional<Card> findByAccount(Account account);
}
