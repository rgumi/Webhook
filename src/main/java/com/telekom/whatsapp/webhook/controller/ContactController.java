package com.telekom.whatsapp.webhook.controller;

import javax.validation.Valid;

import com.telekom.whatsapp.entity.Contact;
import com.telekom.whatsapp.webhook.services.ContactServiceImpl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path="${webhook.api.prefix}contact")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private ContactServiceImpl contactRepositoryService;

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getById(@PathVariable("id") int id){
        logger.info("Received GET Request");
        Contact contact = contactRepositoryService.getContact(id);
        return ResponseEntity.ok(contact);
    }
    @PostMapping("")
    public ResponseEntity<Contact> test(@RequestBody @Valid Contact contact) {
        contactRepositoryService.createContact(contact);

        return ResponseEntity.ok(contact);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") int id) {
        logger.info("Received DELETE Request");
        contactRepositoryService.deleteContact(id);
    }
}