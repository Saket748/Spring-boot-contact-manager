package com.online.Real.controller;

import com.online.Real.dao.ContactRepository;
import com.online.Real.dao.UserRepository;
import com.online.Real.entities.Contact;
import com.online.Real.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;

    //search handler
    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal){
        System.out.println(query);
        User user = this.userRepository.loadUserByUsername(principal.getName());
        List<Contact> contacts = this.contactRepository.findByNameContainingAndUser(query , user);
        return ResponseEntity.ok(contacts);
    }
}
