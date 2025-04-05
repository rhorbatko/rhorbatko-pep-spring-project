package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.exception.MessageException;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public void validateMessage(Message message) throws MessageException{
        String messageText = message.getMessageText();
        Integer postedBy = message.getPostedBy();
        if(messageText == null || messageText.length() == 0){
            throw new MessageException("message can not be blank!");
        }
        if(messageText.length()>=255){
            throw new MessageException("message is too large. Must be less than 255 characters.");
        }
        if(!accountRepository.existsById(postedBy)){
            throw new MessageException("user does not exist.");
        }
    }

    public Message createMessage(Message newMessage) throws MessageException{
        validateMessage(newMessage);
        return messageRepository.save(newMessage);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer message_id){

        Optional<Message> message = messageRepository.findById(message_id);
        if (message.isEmpty()){
            return Optional.empty();
        }else{
            return message;
        }

    }


}
