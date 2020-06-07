package com.hyunjong.book.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);  //내장 WAS를 실행시켜 별도의 외부 WAS(tomcat)을 설치할 필요가 없다.
    }
}

/**
 * 어노테이션
 * 데이터를 위한 데이터를 의미하며, 한 데이터에 대한 설명을 의미하는 데이터
 *
 * @SpringBootApplication
 * 프로젝트 최상단에 위치하는 클래스
 * 스프링 부트는 @springBootApplication이 있는 위치부터 설정을 읽어간다
 * 스프링 부트의 자동설정, 스프링 Bean 읽기와 생성을 자동으로 설정
 *
 * @SpringApplication.run
 * 내장 WAS(Web Application Server) 샐행
 */
