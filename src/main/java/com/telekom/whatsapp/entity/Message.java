package com.telekom.whatsapp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.telekom.whatsapp.error.NotFoundException;
import com.telekom.whatsapp.webhook.services.HashMapConverter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "messages")
public class Message implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 123L;

    @Transient
    private boolean isNew = true;


    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "message_type", nullable = false)
    private String type;

    @Column(name = "current_status")
    // add default status to "received" for Webhook entries
    // other messages need a custom status, e.g. "created" from API
    private String status = "received";

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn = new Date();

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "changed_on")
    private Date updatedOn;

    @Column(name = "status_changed_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusChangedOn = new Date();

    // IMPORTANT
    // It is assumed that the contact exists before creating a message with him 
    // as recipient. Otherwise an error occures
    @ManyToOne
    @JoinColumn(
        name = "recipient_id",
        referencedColumnName = "wa_id")
    @JsonSetter("recipient_id")
    private Contact contact;


    @Valid
    @Column(name = "content")
    @Convert(converter = HashMapConverter.class)
    @NotEmpty(message = "content cannot be null")
    private Map<String, Object> content = new HashMap<>();

    // functions

    public Message() {}

    @Transient
    @Override
    @JsonIgnore
    public boolean isNew() {
        return this.isNew;
    }

     @PrePersist
     @PostLoad
     public void markNotNew() {
         this.isNew = false;
     }

    // setter 

    @JsonAlias({"text"})
    public void setText(Map<String, Object> map) {

        // "body" attribute is required for the request!
        if (!map.containsKey("body")) throw new NotFoundException("Could not find attribute \"body\"");

        content.put("body", map.get("body"));
        System.out.println(content);
    }

    @JsonAlias({"document"})
    public void setDocument(Map<String, Object> map) {

        // "link" attribute is required for the request!
        if (!map.containsKey("link")) throw new NotFoundException("Could not find attribute \"link\"");

        this.content.put("link", map.get("link"));
    }

    @JsonIgnore
    public void setStatus(String status, Date timestamp) {
        this.status = status;
        this.statusChangedOn = timestamp;
    }

    @JsonAlias("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonAlias("type")
    public void setType(String type) {
        this.type = type;
    }

    // getter

    @JsonProperty("content")
    public Map<String, Object> getContent(){
        return this.content;
    }

    @JsonProperty("id")
    @NotNull(message = "id cannot be null")
    @Override
    public String getId() {
        return this.id;
    }

    @JsonProperty("type")
    @NotNull(message = "type cannot be null")
    public String getType() {
        return this.type;
    }

    @JsonProperty("created_on")
    public Date getCreatedOn() {
        return this.createdOn;
    }

    @JsonProperty("current_status")
    public String getStatus() {
        return this.status;
    }

    @JsonProperty("status_update")
    public Date getStatusChangedOn() {
        return this.statusChangedOn;
    }

    @JsonGetter("contact_info")
    public Contact getContact() {
        return this.contact;
    }
}