package org.patikadev.finalprojectbatuhanunverdi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.model.request.AccountRequest;
import org.patikadev.finalprojectbatuhanunverdi.service.AccountService;
import org.patikadev.finalprojectbatuhanunverdi.service.ExchangeService;
import org.patikadev.finalprojectbatuhanunverdi.validator.AccountRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Api(value = "Account Documentation")
public class AccountController {
    private final AccountService accountService;
    private final AccountRequestValidator accountValidator;
    private final ExchangeService exchangeService;

    @PostMapping("/create")
    @ApiOperation(value = "New Account adding method")
    public ResponseEntity<?> createAccount(@RequestBody AccountRequest accountRequest){
        accountValidator.validate(accountRequest);
        accountService.createAccount(accountRequest);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete/{iBan}")
    @ApiOperation(value = "Delete Account by IBAN method")
    public ResponseEntity<?> deleteAccount(@PathVariable String iBan){
        accountService.deleteAccount(iBan);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getCurrency/{baseCurrency}")
    public ResponseEntity<?> getCurrency(@PathVariable String baseCurrency)
    {
        return exchangeService.getExchangeByBaseCurrency(baseCurrency);
    }
}
