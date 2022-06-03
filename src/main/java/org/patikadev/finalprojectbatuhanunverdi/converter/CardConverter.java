package org.patikadev.finalprojectbatuhanunverdi.converter;

import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.entity.Account;
import org.patikadev.finalprojectbatuhanunverdi.entity.Card;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.CardType;
import org.patikadev.finalprojectbatuhanunverdi.exception.BusinessServiceOperationException;
import org.patikadev.finalprojectbatuhanunverdi.model.request.CardRequest;
import org.patikadev.finalprojectbatuhanunverdi.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author Mert Batuhan UNVERDI
 * @since 4.06.2022
 */
@Component
@RequiredArgsConstructor
public class CardConverter {

    private final AccountRepository accountRepository;

    public Card toCard(CardRequest cardRequest) {
        Card card = new Card();
        Account account = accountRepository.findById(cardRequest.getAccount()).orElseThrow(()
                -> new BusinessServiceOperationException.AccountNotFound("Account Not Found"));
        Random random = new Random();
        String randomCardNumber = String.valueOf(random.nextLong(9999999999999999L)).replace(".", "");
        card.setAccount(account);
        card.setCardNumber(randomCardNumber);
        card.setCardPassword(cardRequest.getCardPassword());
        int randomCvv = random.nextInt(100, 999);
        card.setCvv(randomCvv);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 6);
        card.setExpirationDate(calendar.getTime());
        card.setCardType(cardRequest.getCardType());
        if (cardRequest.getCardType().equals(CardType.Credit)) {
            card.setCardLimit(cardRequest.getCardLimit());
        }
        if (cardRequest.getCardType().equals(CardType.Debit)) {
            card.setCardLimit(BigDecimal.ZERO);
        }
        return card;
    }
}
