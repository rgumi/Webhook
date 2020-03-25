package com.telekom.whatsapp.entity;

import java.util.Collection;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Webhook {
    
    @Valid
    private Collection<Message> messages;
    @Valid
    private Collection<Contact> contacts;
    @Valid
    private Collection<Status> statuses;


    // add some metadata
    // queue length, status, version etc.

    @JsonGetter("messages")
    public Collection<Message> getMessages() {
        return this.messages;
    }
    @JsonIgnore
    public Collection<Contact> getContacts() {
        return this.contacts;
    }
    @JsonGetter("statuses")
    public Collection<Status> getStatuses() {
        return this.statuses;
    }

    @JsonSetter("messages")
    public void setMessages(Collection<Message> messages){
        this.messages = messages;
    }

    @JsonSetter("contacts")
    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
    }

    @JsonSetter("statuses")
    public void setStatuses(Collection<Status> statuses) {
        this.statuses = statuses;
    }
}