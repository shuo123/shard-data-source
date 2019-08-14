package com.example.sharddatasource.dao;

import com.example.sharddatasource.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wws
 * @version 1.0.0
 * @date 2019-08-14 11:16
 **/
public interface UserDAO extends JpaRepository<User, Long> {
}
