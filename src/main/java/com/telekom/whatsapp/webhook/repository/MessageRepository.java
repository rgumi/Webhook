package com.telekom.whatsapp.webhook.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.telekom.whatsapp.entity.*;

@Repository
@Transactional
public interface MessageRepository extends CrudRepository<Message, String> {

    List<Message> findAll(Pageable pageable);
    List<Message> findAll();
    // not tested
    // if all messages matching a specific status are requested
    List<Message> findByStatus(String status, Pageable pageable);
    List<Message> findByStatus(String status);
    // not tested
    // find all messages from a contact/customer
    // both send and received (complete history)
    List<Message> findByContactId(int id);

    // return a List<Status> Object created from all messages in DB
    @Query("SELECT new com.telekom.whatsapp.entity.Status(m.id, m.contact.id, m.status, m.statusChangedOn) FROM Message m")
    List<Status> getStatuses();

    @Query("SELECT new com.telekom.whatsapp.entity.Status(m.id, m.contact.id, m.status, m.statusChangedOn) FROM Message m")
    List<Status> getStatuses(Pageable pageable);
}