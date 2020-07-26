package com.hyunjong.book.springboot.web;

import com.hyunjong.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)//테스트를 진행할 때 Junit에 내장된 실행자 외에 다른 실행자를 실행한다, SpringRunner은 스프링 부트 테스트와 Junut 사이에 연결자 역할을 한다.
@WebMvcTest(controllers = HelloController.class,
excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws  Exception{
        String name ="hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name",name)
                .param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
/**
 * @RunWith(SpringRunner.class)
 * JUnit 프레임워크가 Runner를 실행할 떄 @Runwith 어노테이션을 통해 SpringRunner.class라는 확장된 클래스를 실행하라고 지시하는것
 * 즉, 스프링 부트 테스트와 JUite사이에 연결자 역할.
 *
 * @WebMvcTest
 * 여러 스프링 테스트 어노테이션중 Web(Spring MVC)에 집중할 수 있는 어노테이션
 * 선언시 @Controller, @ControllerAdivce등을 사용할수있따.
 * 하지만 @Service, @Component, @Repository등은 사용할수 없다.
 * 여기서는 Controller만 사용하기 때문에 선언한다.
 *
 * @Autowired
 * 스프링이 관리하는 빈(Bean)을 주입
 * Bean 이란 스프링 컨테이너에 의해 만들어진 객체
 *
 * private MockMvc mvc
 * 웹 API를 테스트할 떄 사용한다.
 * 스프링 MVC 테스트의 시작점
 * 이 클래스를 통해 Http Get,Post등에 대한 API테스트를 할 수 있다.
 *
 * mvc.perform(get("/hello"))
 * MockMvc를 통해 /hello 주소로 Http Get 요청을 한다.
 *
 * .andExpect(status().isOk(())
 * mvc.perform의 결과를 검증
 * Http Header의 status를 검증받는다(500,404,200 등), 여기서는 isOk()인 200인지 아닌지를 검증한다.
 *
 * andExpect(content().string(hello))
 * mvc.perform의 결과를 검증
 * 응답 본문의 내용을 검증하는데 Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증한다.
 *
 * param
 * API테스트할 떄 사용될 요청 파라미터를 설정
 * 단, 값은 String만 허용할수 있어 int형인 amount는 String.valueof(amount)로 문자열로 변환해준다음 파라미터로 설정해준다.
 *
 * jsonPath
 * JSON응답값을 필드별로 검증할 수 있는 메소드
 * $를 기준으로 필드명을 명시한다.
 * name과 amount를 검증하니 $.name, $.amount로 검증한다.
 */
