package com.telekom.whatsapp.webhook.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.telekom.whatsapp.webhook.services.interfaces.MessageService;
import com.telekom.whatsapp.entity.Message;
import com.telekom.whatsapp.entity.Status;
import com.telekom.whatsapp.error.*;
import com.telekom.whatsapp.webhook.repository.ContactRepository;
import com.telekom.whatsapp.webhook.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MessageServiceImpl implements MessageService {

    @Value("${webhook.message.page_size}")
    private int pageSize;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ContactRepository contactRepository;

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public void createMessage(Message msg) {
        logger.debug("Saving message into repository");
        // check if the message exists
        // might be useless because this should never happen 
        if (messageRepository.existsById(msg.getId())) throw new ElementExistsException(msg.getId() + " exists already");

        // check if contact of message exists, otherwise TransientPropertyValueException
        if (!contactRepository.existsById(msg.getContact().getId())) throw new NotFoundException("Recipient " + msg.getContact().getId() + " not found");

        messageRepository.save(msg);
        logger.debug("Successfully saved message to repository");
        return;
    }

    @Override
    public Message getMessage(String id) {
        logger.debug("Querying repository for message ("+ id +")");
        Message msg = new Message();

        try {
            msg = messageRepository.findById(id).orElseThrow(() -> new NotFoundException("Message-ID (" + id + ") not found."));
            logger.debug("Successfully found message in repository");

        } catch (NotFoundException err) {
            // assume never happens
            throw err;
        } catch (Exception err) {
            // do something

            logger.error("unhandled error occured");
            logger.error(err.getMessage());
        }
        return msg;
    }

    @Override
    public Collection<Message> getMessagesPaged(String status, int page) {
        logger.debug("Querying all messages in repository");

        Collection<Message> msgCollection = new ArrayList<>();
        
        if (status.equals("*")) messageRepository.findAll(PageRequest.of(page, pageSize)).forEach(msgCollection::add);
        if (!status.equals("*")) messageRepository.findByStatus(status, PageRequest.of(page, pageSize)).forEach(msgCollection::add);
        
        return msgCollection;
    }

    @Override
    public Collection<Message> getMessages(String status) {
        logger.debug("Querying all messages in repository");

        Collection<Message> msgCollection = new ArrayList<>();
        
        if (status.equals("*")) messageRepository.findAll().forEach(msgCollection::add);
        if (!status.equals("*")) messageRepository.findByStatus(status).forEach(msgCollection::add);
        
        return msgCollection;
    }

    @Override
    public void deleteMessage(String id) {
        logger.debug("Deleting message from repository");
        try {
            messageRepository.deleteById(id);
        } catch (EmptyResultDataAccessException err) {
            throw new NotFoundException("Message-ID (" + id + ") not found.");
        } catch (Exception err) {
            // do something

            logger.error("unhandled error occured");
            logger.error(err.getMessage());
        }
    }

    @Override
    public Message updateMessageState(String id, Date timestamp, String newState) {
        Message msgFromDb = this.getMessage(id);
        msgFromDb.setStatus(newState, timestamp);
        messageRepository.save(msgFromDb);
        return msgFromDb;
    }

    @Override
    public Collection<Status> getStatuses() {
        Collection<Status> msgCollection = new ArrayList<>();
        messageRepository.getStatuses().forEach(msgCollection::add);
        return msgCollection;
    }

    @Override
    public Collection<Status> getStatusesPaged(int page) {
        Collection<Status> msgCollection = new ArrayList<>();
        messageRepository.getStatuses(PageRequest.of(page, pageSize)).forEach(msgCollection::add);
        return msgCollection;
    }
}