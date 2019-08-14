package com.example.sharddatasource.dao;

import com.example.sharddatasource.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void add(){
        User user = new User();
        user.setUsername("a");
        userDAO.save(user);
    }

    @Test
    public void get(){
        Optional<User> user = userDAO.findById(1L);
        System.out.println(user.get());
    }

}