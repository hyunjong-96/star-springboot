package com.hyunjong.book.springboot.web;

import com.hyunjong.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController//컨트롤러를 JSON으로 반환하는 컨트롤러로 만들어준다.
public class HelloController {
    @GetMapping("/hello/dto") //HTTP method인 get의 요청을 받을 수 있는 api를 만들어준다.
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){

        return new HelloResponseDto(name, amount);
    }
}
/**
 * @RestController
 * 컨트롤로를 JSON으로 반환하는 컨트롤러로 설정
 *
 * @GetMapping
 * Http Method인 Get의 요청을 받을 수 있는 API설정
 * "/hello"로 요청이 오는 경우 문자열 hello을 반환
 *
 * @RequestParam
 * 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
 * 외부에서 name(@RequestParam("name"))이라는 이름으로넘긴 파라미터를 메소드 파라미터 name(String name)에 저장하게된다.
 */
