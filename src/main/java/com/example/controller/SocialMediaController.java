package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.exception.AccountLoginException;
import com.example.exception.AccountRegistrationException;
import com.example.exception.DuplicateUserException;
import com.example.service.AccountService;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    
    private final AccountService accountService;

    @Autowired
    public SocialMediaController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) throws AccountRegistrationException, DuplicateUserException{
        return ResponseEntity.status(200).body(accountService.register(account));
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AccountLoginException{
        return ResponseEntity.status(200).body(accountService.login(account));
    }

    @ExceptionHandler(AccountRegistrationException.class)
    public ResponseEntity<String> handleAccountRegistrationException(AccountRegistrationException ex){
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> handleDuplicateUserException(DuplicateUserException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(AccountLoginException.class)
    public ResponseEntity<String> handleAccountLoginException(AccountLoginException ex){
        return ResponseEntity.status(401).body(ex.getMessage());
    }


}
