package com.hyunjong.book.springboot.web;

import com.hyunjong.book.springboot.service.PythonTest;
import com.hyunjong.book.springboot.web.dto.HelloResponseDto;
import lombok.RequiredArgsConstructor;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

@RequiredArgsConstructor
@RestController//컨트롤러를 JSON으로 반환하는 컨트롤러로 만들어준다.
public class HelloController {

    private final PythonTest pythonTest;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }


    @GetMapping("/hello/dto") //HTTP method인 get의 요청을 받을 수 있는 api를 만들어준다.
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {

        return new HelloResponseDto(name, amount);
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String upload(HttpServletRequest request, @RequestParam("signal")String signal, @RequestParam("id")String name) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("image");

        return pythonTest.uploadImage(file,signal,name);
    }

    @PostMapping("/test")
    public void test(@RequestBody HashMap <String, Object> map){
        System.out.println(map);
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
