package org.patikadev.finalprojectbatuhanunverdi.controller;

import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.model.request.CardRequest;
import org.patikadev.finalprojectbatuhanunverdi.model.request.DebtPaymentRequest;
import org.patikadev.finalprojectbatuhanunverdi.model.request.DepositRequest;
import org.patikadev.finalprojectbatuhanunverdi.model.request.PaymentRequest;
import org.patikadev.finalprojectbatuhanunverdi.service.CardService;
import org.patikadev.finalprojectbatuhanunverdi.validator.CardRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mert Batuhan UNVERDI
 * @since 2.06.2022
 */
@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final CardRequestValidator cardRequestValidator;

    @PostMapping("/create")
    public ResponseEntity<?> createCard(@RequestBody CardRequest cardRequest) {
        cardRequestValidator.validate(cardRequest);
        cardService.createCard(cardRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payment")
    public ResponseEntity<?> paymentByCard(@RequestBody PaymentRequest paymentRequest) {
        cardService.paymentByCard(paymentRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> depositByATM(@RequestBody DepositRequest depositRequest) {
        cardService.depositMoney(depositRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/debtPay")
    public ResponseEntity<?> deptPayByAccount(@RequestBody DebtPaymentRequest debtPaymentRequest) {
        cardService.debtPaymentByAccount(debtPaymentRequest);
        return ResponseEntity.ok().build();
    }
}
