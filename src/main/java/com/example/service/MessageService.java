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
// validating a new message to be created
    public void validateMessage(Message message) throws MessageException{
        String messageText = message.getMessageText();
        Integer postedBy = message.getPostedBy();
        if(messageText == null || messageText.strip().length() == 0){
            throw new MessageException("message can not be blank!");
        }
        if(messageText.length()>=255){
            throw new MessageException("message is too large. Must be less than 255 characters.");
        }
        if(!accountRepository.existsById(postedBy)){
            throw new MessageException("user does not exist.");
        }
    }
// validating incoming updates to a message
    public void validateMessage(String message_text, Integer message_id) throws MessageException{
        if(!messageRepository.existsById(message_id)){
            throw new MessageException("message does not exist");
        }
        if(message_text == null || message_text.strip().length() == 0){
            throw new MessageException("message can not be blank!");
        }
        if(message_text.length()>=255){
            throw new MessageException("message is too large. Must be less than 255 characters.");
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

    public Integer deleteMessageById(Integer message_id){
        if(messageRepository.existsById(message_id)){
            messageRepository.deleteById(message_id);
            return 1;
        }
        return null;
    }

    public Integer patchMessageById(Integer message_id, String message_text) throws MessageException{
        validateMessage(message_text, message_id);
        Message messageToUpdate = messageRepository.getById(message_id);
        messageToUpdate.setMessageText(message_text);
        messageRepository.save(messageToUpdate);
        return 1;
    }


}
