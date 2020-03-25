package com.telekom.whatsapp.webhook.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.telekom.whatsapp.entity.*;

@Repository
@Transactional
public interface ContactRepository extends CrudRepository<Contact, Integer> {
}