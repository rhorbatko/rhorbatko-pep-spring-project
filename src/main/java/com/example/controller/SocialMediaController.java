package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.*;
import com.example.exception.*;
import com.example.service.*;
import java.util.List;
import java.util.Optional;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) throws AccountRegistrationException, DuplicateUserException{
        return ResponseEntity.status(200).body(accountService.register(account));
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AccountLoginException{
        return ResponseEntity.status(200).body(accountService.login(account));
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage) throws MessageException{
        return ResponseEntity.status(200).body(messageService.createMessage(newMessage));
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessagesById(@PathVariable Integer message_id){
        Optional<Message> response = messageService.getMessageById(message_id);

        if(response.isPresent()){

            return ResponseEntity.status(200).body(response.get());
        }
        return ResponseEntity.status(200).build();
        
    }
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessagesById(@PathVariable Integer message_id){
        Integer numOfRowsAffected = messageService.deleteMessageById(message_id);

        if(numOfRowsAffected != null){
            return ResponseEntity.status(200).body(numOfRowsAffected);
        }
        return ResponseEntity.status(200).build();
        
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

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<String> handleMessageException(MessageException ex){
        return ResponseEntity.status(400).body(ex.getMessage());
    }


}
