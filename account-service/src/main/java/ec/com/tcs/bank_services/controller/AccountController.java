package ec.com.tcs.bank_services.controller;

import ec.com.tcs.bank_services.dto.request.AccountRequest;
import ec.com.tcs.bank_services.dto.request.AccountUpdateRequest;
import ec.com.tcs.bank_services.dto.response.AccountResponse;
import ec.com.tcs.bank_services.model.AccountEntity;
import ec.com.tcs.bank_services.service.IAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    
    @Autowired
    private IAccountService accountService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountEntity> getAccount(@PathVariable String accountNumber) {
        AccountEntity Account = accountService.getAccount(accountNumber);
        if(ObjectUtils.isEmpty(Account)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Account);
    }
    
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountRequest account) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(account));
    }


    @PutMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable String accountNumber, @RequestBody @Valid AccountUpdateRequest account) {
        return ResponseEntity.ok(accountService.updateAccount(accountNumber,account));
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String accountNumber) {
        Boolean delete = accountService.deleteAccount(accountNumber);
        if(!ObjectUtils.isEmpty(delete) && delete){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
