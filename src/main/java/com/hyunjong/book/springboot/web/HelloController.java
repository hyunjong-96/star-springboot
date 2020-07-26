package com.hyunjong.book.springboot.web;

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
import java.io.IOException;
import java.util.Properties;

@RequiredArgsConstructor
@RestController//컨트롤러를 JSON으로 반환하는 컨트롤러로 만들어준다.
public class HelloController {

    private static PythonInterpreter intPre;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }


    @GetMapping("/hello/dto") //HTTP method인 get의 요청을 받을 수 있는 api를 만들어준다.
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {

        return new HelloResponseDto(name, amount);
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String upload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("image");

        System.out.println(file.getName());
        System.out.println(file.getSize());
        System.out.println(file.getOriginalFilename());
        byte[] data = file.getBytes();

        return "success";
    }

    @GetMapping("/python/test")
    public String test() {
        Properties props = new Properties();
        props.put("python.home", "C:/Users/jython-standalone-2.7.2");
        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.import.site", "false");

        Properties preprops = System.getProperties();

        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter intPre = new PythonInterpreter();

        intPre.execfile("src/main/java/com/hyunjong/book/springboot/web/test.py");
        intPre.exec("print(testFunc(5,10))");

        PyFunction pyFunction = (PyFunction) intPre.get("testFunc", PyFunction.class);
        int a = 10, b = 20;
        PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println(pyobj.toString());

        return pyobj.toString();
        // return "test";
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
