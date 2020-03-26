package com.telekom.whatsapp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "contacts")
public class Contact implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3737861051528659900L;

    @Id
    @Column(name = "wa_id",
            nullable = false)
    @NotNull(message = "wa_id cannot be null")
    private int id;
    
    @Column(name = "profile_name",
            nullable = false)
    @NotNull(message = "profile_name cannot be null")
    private String profileName;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn = new Date();

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "changed_on")
    private Date updatedOn;

    @OneToMany(
        mappedBy = "contact", 
        cascade = CascadeType.ALL)
    private List<Message> messages;


    // functions

    public Contact() {
    }

    public Contact(int id) {
        this.id = id;
    }

    public Contact(int id, String profileName) {
        this.id = id;
        this.profileName = profileName;
    }

    @JsonProperty("profile")
    public void setProfileName(Map<String, String> map) {
        this.profileName = map.get("name");
    }

    @JsonProperty("wa_id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("wa_id")
    public int getId() {
        return this.id;
    }

    @JsonProperty("profile_name")
    public String getProfileName() {
        return this.profileName;
    }

    @JsonProperty("created_on")
    public Date getCreatedOn() {
        return this.createdOn;
    }
    @JsonIgnore
    public Date getUpdatedOn() {
        return this.updatedOn;
    }

    @JsonProperty("href")
    @Transient
    public String getHref() {
        // TODO: implement AppConfigurator
        String baseUri = "http://localhost:8080";
        return baseUri + "/v1/contact/" + this.id;
    }
}