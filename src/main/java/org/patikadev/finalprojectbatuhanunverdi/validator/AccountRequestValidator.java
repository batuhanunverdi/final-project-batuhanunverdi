package org.patikadev.finalprojectbatuhanunverdi.validator;

import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountCurrency;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountType;
import org.patikadev.finalprojectbatuhanunverdi.exception.ValidationOperationException;
import org.patikadev.finalprojectbatuhanunverdi.model.request.AccountRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
@Component
public class AccountRequestValidator {
    public void validate(AccountRequest accountRequest){
        if(Objects.isNull(accountRequest)){
            throw new ValidationOperationException.AccountNotValid("Account can not be null or empty");
        }
        if(!(StringUtils.hasLength(accountRequest.getAccountType().toString()))){
            throw new ValidationOperationException.AccountNotValid("Account type can not be null or empty");
        }
        if(!(accountRequest.getAccountType().toString().equals(AccountType.Deposit.toString()))&&
                !(accountRequest.getAccountType().toString().equals(AccountType.Checking.toString())))
        {
            throw new ValidationOperationException.AccountNotValid("Account type should be DemandDeposit or Accumulation");
        }
        if(!(StringUtils.hasLength(accountRequest.getAccountCurrency().toString()))){
            throw new ValidationOperationException.AccountNotValid("Account currency can not be null or empty");
        }
        if(!(accountRequest.getAccountCurrency().toString().equals(AccountCurrency.TRY.toString()))&&
                !(accountRequest.getAccountCurrency().toString().equals(AccountCurrency.EUR.toString()))&&
                !(accountRequest.getAccountCurrency().toString().equals(AccountCurrency.USD.toString()))){
            throw new ValidationOperationException.AccountNotValid("Account Currency should be EUR, USD or TRY");
        }

    }
}
