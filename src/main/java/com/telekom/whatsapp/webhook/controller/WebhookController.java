package com.telekom.whatsapp.webhook.controller;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import com.telekom.whatsapp.entity.Webhook;
import com.telekom.whatsapp.webhook.services.ContactServiceImpl;
import com.telekom.whatsapp.webhook.services.MessageServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path="${webhook.api_prefix}v1")
class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    @Autowired
    private MessageServiceImpl messageRepositoryService;

    @Autowired
    private ContactServiceImpl contactServiceImpl;

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/webhook",
        consumes = "application/json",
        produces = "application/json; charset=UTF-8"
    )
    public void create(@RequestBody @Valid Webhook webhook) {
        logger.info("Received POST Request");

        // assume contact is first

        if (webhook.getContacts() != null) webhook.getContacts().forEach(contact -> contactServiceImpl.createContact(contact));

        if (webhook.getMessages() != null) webhook.getMessages().forEach(msg -> {
            // this message was received by webhook therefore from a customer
            // the default status for a customer message is received
            msg.setStatus("received", new Date());

            // perist message in DB
            messageRepositoryService.createMessage(msg);
        });

        // this is called when a status update is pushed from the webhook
        // status updates occure only on outbound messages (sent, deliverd, read) as per WhatsApp checkmark
        if (webhook.getStatuses() != null) webhook.getStatuses().forEach(msg -> 
            messageRepositoryService.updateMessageState(msg.getMsgId(), msg.getTimestamp(), msg.getStatus()));        
    }


    @RequestMapping(
        method = RequestMethod.GET,
        value = "/news",
        produces = "application/json; charset=UTF-8"
    )
    public ResponseEntity<Webhook>  getNews(@RequestParam(required = false) Optional<Integer> page){
        logger.info("Received GET Request");
        Webhook wh = new Webhook();

        if (page.isPresent()) {
            wh.setStatuses(messageRepositoryService.getStatusesPaged(page.get()));
            wh.setMessages(messageRepositoryService.getMessagesPaged("received", page.get()));
        } else {
            wh.setStatuses(messageRepositoryService.getStatuses());
            wh.setMessages(messageRepositoryService.getMessages("received"));     
        }

        // set status of pulled messages to "pulled" -> client does that 
        // with endpoint PATCH /message/{id}
        
        return ResponseEntity.ok(wh);
    }
}