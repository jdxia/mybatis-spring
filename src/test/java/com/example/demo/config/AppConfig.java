package com.example.demo.config;

import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@ComponentScan("com.example.demo.**")
@MapperScan("com.example.demo.mapper")
@EnableTransactionManagement
@Configuration
public class AppConfig {
  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }

  /**
   * DataSourceTransactionManager: 处理 JDBC 事务
   * JpaTransactionManager: 处理 JPA 事务
   */
  @Bean
  public PlatformTransactionManager transactionManager() {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    transactionManager.setDataSource(dataSource());
    return transactionManager;
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8&useSSL=false&verifyServerCertificate=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
    dataSource.setUsername("root");
    dataSource.setPassword("hello.world123");
    return dataSource;
  }


  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource());

    org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
    configuration.setLogImpl(Log4jImpl.class);
    sessionFactoryBean.setConfiguration(configuration);

    return sessionFactoryBean.getObject();
  }

  @Bean
  public SqlSessionTemplate sqlSession() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory());
  }
}
