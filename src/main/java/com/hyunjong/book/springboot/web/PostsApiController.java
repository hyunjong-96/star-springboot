package com.hyunjong.book.springboot.web;


import com.hyunjong.book.springboot.service.PostsService;
import com.hyunjong.book.springboot.web.dto.PostsResponseDto;
import com.hyunjong.book.springboot.web.dto.PostsSaveRequestDto;
import com.hyunjong.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor //final로 선언된 필드가 포함된 생성자를 생성
@RestController //컨트롤러를 JSON으로 반환
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }
}
/**
 * @RequireArgsConstructor
 * 생성자로 Bean을 주입받아 해당 클래스의 의존성 관계가 변경될 떄마다 생산자 코드를 계속해서 수정하는 번거로움을 해결하기 위함.
 */
