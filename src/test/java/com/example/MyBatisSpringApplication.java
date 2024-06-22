/**
 * Copyright 2009-2024 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example;

import com.example.demo.config.AppConfig;
import com.example.demo.service.UserService;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyBatisSpringApplication {

  /**
   * 在整合Mybatis的时候，我们需要定义一个，MapperScannerConfigurer，指定一个basePackage扫描包实现Mapper的自动扫描，
   * MapperScannerConfigurer 实现 BeanDefinitionRegistryPostProcessor一个BeanFactory 后置处理器 ，在Spring启动的过程中会触发
   * postProcessBeanDefinitionRegistry 方法的执行 该方法会扫描 basePackages 包下的 Mapper, 然后把Mapper封装成一个一个的 MapperFactoryBean,
   *  <b> 核心知识点 : 也就是说 Mapper 是通过 MapperFactoryBean 来创建的, 方法是 MapperFactoryBean#getObject() </b>
   * <p>
   * MapperFactoryBean 继承了SqlSessionDaoSupport 其中提供了一个SqlSessionTemplate来管理 SqlSession，
   * 而在 SqlSessionTemplate 中是通过动态代理来创建的SqlSession。也就是说在SqlSessionTemplate中管理了SqlSession的代理类。
   * <p>
   * 创建出来的动态代理 mapper 里面会有 sqlSessionTemplate
   *
   * <p>
   *   <b> 核心知识点 执行sql核心是:  sqlSessionTemplate 里面的 SqlSessionInterceptor#invoke </b>
   * <p>
   *
   * <p>
   * 因为我们 SqlSession 的默认实现 DefaultSqlSession 是线程不安全的，所以Spring使用了 SqlSessionTemplate 一个线程安全的类来管理 DefaultSqlSession
   *
   * <p>
   * SqlSessionTemplate 是Spring管理的线程安全的SqlSession，它与 Spring 事务管理一起工作，以确保实际使用的 SqlSession 是与当前 Spring 事务相关联的。
   * 此外，它还管理会话生命周期，包括根据 Spring 事务配置根据需要关闭、提交或回滚会话
   * <p>
   * <b>在 TransactionSynchronizationManager 中有个 ThreadLocal< Map < Object, Object > > </b>
   * resources我们的SqlSession就是被封装成SelSessionHolder 然后缓存到该ThreadLocal中， 所以 SqlSessionTemplate是如何保证线程安全？
   * 答案是：通过一个ThreadLocal来缓存SqlSession来保证线程安全，如果ThreadLocal没有SqlSession就会通过SqlSessionFactory#openSession创建一个，再缓存到ThreadLocal中。
   * 这一切的动作都是交给SqlSessionTemplate来管理的。所以它是线程安全的。
   */
  public static void main(String[] args) throws Exception {
    testAnno();
  }

  public static void testAnno() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    UserService userService = ac.getBean(UserService.class);

    userService.findAll();

    userService.findAllTransactional();

    System.out.println("=====================> 执行完毕");
  }


  public static void testXML() {
    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");

    UserService userService = ctx.getBean(UserService.class);

    userService.findAll();

    userService.findAllTransactional();

    System.out.println("=====================> 执行完毕");
  }

}
