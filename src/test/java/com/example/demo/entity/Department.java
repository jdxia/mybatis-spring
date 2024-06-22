package com.example.demo.entity;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class Department implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private String name;

  private String tel;

  private Set<User> users;
}
