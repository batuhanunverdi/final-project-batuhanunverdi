package org.patikadev.finalprojectbatuhanunverdi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.model.request.TransferRequest;
import org.patikadev.finalprojectbatuhanunverdi.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mert Batuhan UNVERDI
 * @since 21.05.2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transfers")
@Api(value = "Transfer Documentation")
public class TransferController {
    private final TransferService transferService;
    @PostMapping()
    @ApiOperation(value = "Transfer Money Method")
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequest transferRequest) throws NoSuchFieldException {
        transferService.moneyTransferControl(transferRequest.getSenderIBan(),
                transferRequest.getReceiverIBan(),transferRequest.getBalance());
        return ResponseEntity.ok().build();
    }
}
