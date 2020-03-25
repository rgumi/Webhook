package com.telekom.whatsapp.webhook.services.interfaces;

import java.util.Collection;
import java.util.Date;

import com.telekom.whatsapp.entity.*;
import com.telekom.whatsapp.error.NotFoundException;


public interface MessageService {
    // Create a single message in the DB
    public abstract void createMessage(Message msg);
    // Get a message matching the given ID
    public abstract Message getMessage(String id) throws NotFoundException;
    // Get all messages matching status
    // "*" => all
    public abstract Collection<Message> getMessages(String status);
    // Delete a message, e.g. when it was pulled by client
    public abstract void deleteMessage(String id) throws NotFoundException;

    // Update message status (sent, delivered, read, etc.)
    // timestamp could be omitted if Service set current timestamp...
    public abstract Message updateMessageState(String id, Date timestamp, String newState) throws NotFoundException;

    // returns Statuses of all messages
    public abstract Collection<Status> getStatuses();
}