package com.telekom.whatsapp.error;

import java.util.Date;
import java.util.List;

public class ExceptionResponse {
    
    private Date timestamp;
    private List<String> messages;
    private String details;
    private String httpCodeMessage;

    public ExceptionResponse(Date timestamp, List<String> messages, String details, String httpCodeMessage) {
      super();
      this.timestamp = timestamp;
      this.messages = messages;
      this.details = details;
      this.httpCodeMessage=httpCodeMessage;
    }

    public ExceptionResponse(Date timestamp, List<String> messages, String httpCodeMessage) {
      super();
      this.timestamp = timestamp;
      this.messages = messages;
      this.httpCodeMessage = httpCodeMessage;
    }

    public String getHttpCodeMessage() {
      return httpCodeMessage;
    }
    public Date getTimestamp() {
      return timestamp;
    }
    public List<String> getMessages() {
      return messages;
    }
    public String getDetails() {
      return details;
    }
  }