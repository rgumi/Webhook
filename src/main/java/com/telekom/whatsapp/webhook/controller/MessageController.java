package com.telekom.whatsapp.webhook.controller;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import com.telekom.whatsapp.entity.Message;
import com.telekom.whatsapp.webhook.services.MessageServiceImpl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path="${webhook.api_prefix}v1/message")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageServiceImpl messageRepositoryService;

    @GetMapping("/{id}")
    public ResponseEntity<Message> getByID(@PathVariable("id") String id){
        logger.info("Received GET Request");
        Message msg = messageRepositoryService.getMessage(id);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("")
    public ResponseEntity<Collection<Message>> all() {

        logger.info("Received GET Request");
        Collection<Message> msgs = messageRepositoryService.getMessages("*");
        return ResponseEntity.ok(msgs);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {

        logger.info("Received DELETE Request");
        messageRepositoryService.deleteMessage(id);
    }

    @PatchMapping("/{id}")
    public void updateStatus(@PathVariable("id") String id) {
        logger.info("Received PATCH Request");
        Date timestamp = new Date();
        String newState = "pulled";
        messageRepositoryService.updateMessageState(id, timestamp, newState);
    }

    // how to handle errors?
    // when 1 message is not found or another error occures then the whole request results in an error
    // if the request was handled partly then client needs to know which id were patched/deleted successfully
    @PatchMapping("")
    public void updateStatuses(@RequestBody Collection<String> messages) {
        logger.info("Received PATCH Request");
        Date timestamp = new Date();
        String newState = "pulled";
        messages.forEach(messageId -> {
            messageRepositoryService.updateMessageState(messageId, timestamp, newState);
        });
    }

    @PostMapping("")
    public void createMessage(@RequestBody @Valid Message msg) {
        messageRepositoryService.createMessage(msg);
    }
}