package org.patikadev.finalprojectbatuhanunverdi.model.request;

import lombok.Getter;
import lombok.Setter;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountCurrency;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountType;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
@Getter
@Setter
public class AccountRequest {
    private String token;
    private AccountType accountType;
    private AccountCurrency accountCurrency;
}
