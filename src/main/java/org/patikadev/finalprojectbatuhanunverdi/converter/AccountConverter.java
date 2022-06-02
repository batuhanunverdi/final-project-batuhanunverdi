package org.patikadev.finalprojectbatuhanunverdi.converter;

import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.entity.Account;
import org.patikadev.finalprojectbatuhanunverdi.entity.User;
import org.patikadev.finalprojectbatuhanunverdi.helper.JWTHelper;
import org.patikadev.finalprojectbatuhanunverdi.model.request.AccountRequest;
import org.patikadev.finalprojectbatuhanunverdi.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
@Component
@RequiredArgsConstructor
public class AccountConverter {
    private final UserRepository userRepository;
    private final JWTHelper jwtHelper;
    public Account toAccount(AccountRequest accountRequest){

        Account account = new Account();
        Optional<User> user = userRepository.findByTc(jwtHelper.findByUsername(accountRequest.getToken()));
        Random random = new Random();
        String randomIban = String.valueOf(random.nextInt(999999)).replace(".","");
        account.setAccountCurrency(accountRequest.getAccountCurrency());
        account.setUser(user.get());
        account.setAccountType(accountRequest.getAccountType());
        account.setIBAN("TR "+ randomIban);
        account.setBalance(BigDecimal.ZERO);
        return account;

    }
}
