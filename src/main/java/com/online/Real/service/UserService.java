package com.online.Real.service;

import java.io.File;
import java.util.Optional;

import javax.transaction.Transactional;

import com.online.Real.dao.ProjectRepository;
import com.online.Real.entities.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.online.Real.dao.ContactRepository;
import com.online.Real.dao.UserRepository;
import com.online.Real.entities.Contact;
import com.online.Real.entities.User;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	ContactRepository contacRepository;
	@Autowired
	ProjectRepository projectRepository;
	public User userRegister(User user) {
		System.out.println("userService : " + user);
		return userRepository.save(user);
	}

	public User findUserByEmail(String email){
		User resultUser = userRepository.loadUserByUsername(email);
		return resultUser;
	}

	public User addContactInUser(User user ) {
		User result = userRepository.save(user);
		return result;
	}


	
	/** get all contacts list with respective users UserID */
	public Page<Contact> getContactsList(int userId, Pageable pageable){
		Page<Contact> listContactsByUser = this.contacRepository.getContactsByUser(userId, pageable);
		return listContactsByUser;
	}
	/** getting respective contact details */
	public Contact getContactDetail(int cId) {
		Optional<Contact> optionalContact =  this.contacRepository.findById(cId);
		Contact contact = optionalContact.get();
		return contact;
	}
	/** find contact info by using user ID **/
	public Contact getContactById(int cId) {
		Optional<Contact> optionalContact = this.contacRepository.findById(cId);
		Contact contact = optionalContact.get();
		return contact;
	}
	
	/** delete contact by using ID */
	
	public void deleteContact(User user, Contact contact) {
		
		try {
				
		/** It is not deleted directly because its is mapped with user */
		user.getContacts().remove(contact);
			
		//contact.setUser(null);
		//this.contacRepository.delete(contact);
		
		// Now we must delete photo from folder
		 File saveFile = new ClassPathResource("/static/image").getFile();
		 
		File deleteFile = new File(saveFile,contact.getImage());
		deleteFile.delete();
		System.out.println(contact.getcId()+"ID Contact deleted successfully ");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public Contact updateContactInUser(Contact contact) {
		Contact saveContact = this.contacRepository.save(contact);
		return saveContact;
	}
	/***/

	/**
	 * all project by id
	 */
//	public Page<Projects> getProjectsList(int userId, Pageable pageable){
//		Page<Projects> listProjectsByUser = this.projectRepository.getProjectsByUser(userId, pageable);
//		return listProjectsByUser;
//	}

	
}
