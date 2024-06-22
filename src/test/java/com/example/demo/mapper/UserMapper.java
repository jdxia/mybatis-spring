package com.example.demo.mapper;

import com.example.demo.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {

  /**
   * CREATE TABLE `tbl_user` ( `id` varchar(32) NOT NULL, `version` int NOT NULL DEFAULT '0', `name` varchar(32) NOT
   * NULL, `age` int DEFAULT NULL, `birthday` datetime DEFAULT NULL, `department_id` varchar(32) NOT NULL, `sorder` int
   * NOT NULL DEFAULT '1', `deleted` tinyint(1) NOT NULL DEFAULT '0', PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT
   * CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
   */

  @Select("SELECT * FROM tbl_user")
  List<User> findAll();

}
