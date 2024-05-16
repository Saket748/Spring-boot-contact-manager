package com.online.Real.dao;

import com.online.Real.entities.Contact;
import com.online.Real.entities.Projects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.websocket.server.PathParam;

public interface ProjectRepository extends JpaRepository<Contact,Integer> {
    @Query("from Projects as d WHERE d.user.id= :userId")
    // currebt page
    // contact per page - 5
    public Page<Projects> getProjectsByUser(@PathParam("userId") int userId, Pageable pagiable);

}
