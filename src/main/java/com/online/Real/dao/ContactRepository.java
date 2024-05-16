package com.online.Real.dao;

import javax.websocket.server.PathParam;

import com.online.Real.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.online.Real.entities.Contact;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer>{
		
		@Query("from Contact as c WHERE c.user.id= :userId")
		// currebt page
		// contact per page - 5
		public Page<Contact> getContactsByUser(@PathParam("userId") int userId, Pageable pagiable);

		public List<Contact> findByNameContainingAndUser(String name, User user);


}
