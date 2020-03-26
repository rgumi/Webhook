package com.telekom.whatsapp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Status implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8407480617917665992L;
    
    private String msgId;
    private String status;
    private int contactId;
    private Date timestamp;


    public Status() {
    }
   
    public Status(String msgId, int contactId, String status, Date timestamp) {
        this.msgId = msgId;
        this.contactId = contactId;
        this.status = status;
        this.timestamp = timestamp;
    }
    // setter

    @JsonAlias("timestamp")
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @JsonAlias({"contact_id", "recipient_id"})
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    @JsonAlias({"msg_id", "id"})
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @JsonAlias("status")
    public void setStatus(String status) {
        this.status = status;
    }

    // getter 

    @JsonProperty("contact_id")
    @NotNull(message = "contact_id cannot be null")
    public int getContactId() {
        return this.contactId;
    }

    @JsonProperty("status")
    @NotNull(message = "status cannot be null")
    public String getStatus() {
        return this.status;
    }

    @JsonProperty("timestamp")
    @NotNull(message = "timestamp cannot be null")
    public Date getTimestamp() {
        return this.timestamp;
    }

    @JsonProperty("msg_id")
    @NotNull(message = "msg_id cannot be null")
    public String getMsgId() {
        return this.msgId;
    }

    @JsonProperty("href")
    public String getHref() {
        // TODO: implement AppConfigurator
        String baseUri = "http://localhost:8080";
        return baseUri + "/v1/message/" + this.msgId;
    }
}