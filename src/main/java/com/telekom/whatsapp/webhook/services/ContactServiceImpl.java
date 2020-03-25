package com.telekom.whatsapp.webhook.services;

import com.telekom.whatsapp.webhook.services.interfaces.ContactService;
import com.telekom.whatsapp.entity.Contact;
import com.telekom.whatsapp.error.NotFoundException;
import com.telekom.whatsapp.webhook.repository.ContactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public void createContact(Contact contact) {
        logger.debug("Saving contact into repository");
        contactRepository.save(contact);
        logger.debug("Successfully saved contact to repository");
        return;
    }

    @Override
    public Contact getContact(int id) {
        logger.debug("Querying repository for contact ("+ id +")");
        Contact contact = new Contact();
        
        try {
            contact = contactRepository.findById(id).orElseThrow(() -> new NotFoundException("Contact-ID (" + id + ") not found."));
            logger.debug("Successfully found contact in repository");

        } catch (NotFoundException err) {
            
            throw err;
        } catch (Exception err) {
            // do something

            logger.error("unhandled error occured");
            logger.error(err.getMessage());
        }
        return contact;
    }

    @Override
    public void deleteContact(int id) {
        logger.debug("Deleting contact from repository");
        try {
            contactRepository.deleteById(id);
        } catch (EmptyResultDataAccessException err) {
            throw new NotFoundException("Contact-ID (" + id + ") not found.");
        } catch (Exception err) {
            // do something

            logger.error("unhandled error occured");
            logger.error(err.getMessage());
        }
    }
}