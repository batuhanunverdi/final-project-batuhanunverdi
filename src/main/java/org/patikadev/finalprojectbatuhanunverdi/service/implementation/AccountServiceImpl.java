package org.patikadev.finalprojectbatuhanunverdi.service.implementation;

import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.converter.AccountConverter;
import org.patikadev.finalprojectbatuhanunverdi.entity.Account;
import org.patikadev.finalprojectbatuhanunverdi.exception.BusinessServiceOperationException;
import org.patikadev.finalprojectbatuhanunverdi.model.request.AccountRequest;
import org.patikadev.finalprojectbatuhanunverdi.repository.AccountRepository;
import org.patikadev.finalprojectbatuhanunverdi.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    @Override
    public void createAccount(AccountRequest accountRequest) {
        Account account = accountConverter.toAccount(accountRequest);
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(String iBan) {
        Optional<Account> account = accountRepository.findByIBAN(iBan);
        if (account.isEmpty()) {
            throw new BusinessServiceOperationException.AccountNotFound("Account not found");
        }
        if (account.get().getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessServiceOperationException.AccountHasMoneyException("You have money in this account. " +
                    "You cant delete this account without withdrawing the money");
        }
        accountRepository.deleteById(account.get().getId());
    }
}
