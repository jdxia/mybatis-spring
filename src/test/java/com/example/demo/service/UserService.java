package com.example.demo.service;

import com.example.demo.mapper.UserMapper;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserService {

  @Autowired
  private UserMapper userMapper;

  @Transactional
  public void findAllTransactional() {
    userMapper.findAll().forEach(System.out::println);
  }

  public void findAll() {
    userMapper.findAll().forEach(System.out::println);
  }

}
