package com.example.sharddatasource.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author wws
 * @version 1.0.0
 * @date 2019-08-14 11:16
 **/
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;

}
