package org.patikadev.finalprojectbatuhanunverdi.validator;

import org.patikadev.finalprojectbatuhanunverdi.entity.enums.CardType;
import org.patikadev.finalprojectbatuhanunverdi.exception.ValidationOperationException;
import org.patikadev.finalprojectbatuhanunverdi.model.request.CardRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Mert Batuhan UNVERDI
 * @since 2.06.2022
 */
@Component
public class CardRequestValidator {
    public void validate(CardRequest cardRequest) {
        if (Objects.isNull(cardRequest)) {
            throw new ValidationOperationException.CardNotValidException("Card can not be null or empty");
        }
        if(String.valueOf(cardRequest.getCardPassword()).length()!=4){
            throw new ValidationOperationException.CardNotValidException("Card Password must be 4 digits and mus be a number");
        }
        if(!cardRequest.getCardType().toString().equals(CardType.Debit.toString())
                && !cardRequest.getCardType().toString().equals(CardType.Credit.toString())){
            throw new ValidationOperationException.CardNotValidException("Card Type must be Credit or Debit");
        }
    }
}
