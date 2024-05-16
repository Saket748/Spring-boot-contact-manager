package com.online.Real.dao;

import com.online.Real.entities.Myorders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyorderRepository extends JpaRepository<Myorders,Long> {
    public Myorders findByOrderId(String orderId);

}
