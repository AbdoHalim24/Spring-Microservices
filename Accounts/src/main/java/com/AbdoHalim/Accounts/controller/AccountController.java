package com.AbdoHalim.Accounts.controller;


import com.AbdoHalim.Accounts.Constants.AccountConstants;
import com.AbdoHalim.Accounts.dto.AccountDto;
import com.AbdoHalim.Accounts.dto.CustomerDto;
import com.AbdoHalim.Accounts.dto.ResponseDto;
import com.AbdoHalim.Accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto>createAccount(@RequestBody CustomerDto customerDto){
        accountService.createAccount(customerDto);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201,AccountConstants.MESSAGE_201));
    }

}
