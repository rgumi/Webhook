package com.telekom.whatsapp.webhook.services.interfaces;

import com.telekom.whatsapp.entity.*;
import com.telekom.whatsapp.error.NotFoundException;


public interface ContactService {
    // Crate a new Contact
    public abstract void createContact(Contact contact);
    // Get the Contact matching the given ID
    public abstract Contact getContact(int id) throws NotFoundException;
    // Delete the contact for whatever reason... ?
    public abstract void deleteContact(int id) throws NotFoundException;
    // Update the Status of the Contact matching the given ID
    // Only necessary if the Hub needs to check the Datenschutz 
    // public abstract void updateContactStatus(int id, String status) throws ContactNotFound;
}