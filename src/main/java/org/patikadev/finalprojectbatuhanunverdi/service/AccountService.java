package org.patikadev.finalprojectbatuhanunverdi.service;

import org.patikadev.finalprojectbatuhanunverdi.model.request.AccountRequest;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
public interface AccountService {
    void createAccount(AccountRequest accountRequest);
    void deleteAccount(String IBan);
}
