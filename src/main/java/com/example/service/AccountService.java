package com.example.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import com.example.entity.Account;
import com.example.exception.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public void validateUsername(String username) throws DuplicateUserException, AccountRegistrationException{
        if(username == null || username.length() == 0){
            throw new AccountRegistrationException("Username can not be blank");
        }

        Optional<Account> account = accountRepository.findByUsername(username);
        if(account.isPresent()){
            throw new DuplicateUserException("Account with this username already exists");
        }
    }

    public void validatePassword(String password) throws AccountRegistrationException{
        if(password.length() < 4){
            throw new AccountRegistrationException("password is too short. Must be at least 4 characters!");
        }
    }

    public Account register(Account account) throws AccountRegistrationException, DuplicateUserException{

        validateUsername(account.getUsername());
        validatePassword(account.getPassword());
        return accountRepository.save(account);
    }

    
}
