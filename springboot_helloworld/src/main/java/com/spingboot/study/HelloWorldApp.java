package com.spingboot.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWorldApp: Hello World 启动类
 *
 * @author: MrServer
 * @since: 17/9/26 下午5:12
 */
// 申明让spring boot自动给程序进行必要的配置，
// 等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan
@SpringBootApplication
@RestController
public class HelloWorldApp {


    public static void main(String[] args){
        SpringApplication.run(HelloWorldApp.class,args);
    }


    @GetMapping(value = "/test")
    public String hello(){
        return "hello, 欢迎使用Spring Boot";
    }

    /**
     * post 方式
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String post(){
        return "hello, 欢迎使用Spring Boot, post";
    }

}
