package com.hyunjong.book.springboot.web;


import com.hyunjong.book.springboot.domain.posts.Posts;
import com.hyunjong.book.springboot.domain.posts.PostsRepository;
import com.hyunjong.book.springboot.web.dto.PostsSaveRequestDto;
import com.hyunjong.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
/**
 * @WebMvcTest를 사용하지 않는 이유는 @WebMvcTest의 경우 JPA기능이 작동하지 않기 떄문에
 * controller와 같은 외부 연동 관련 부분만 활성화 되기 때문에 JPA기능까지 테스트 할 떄는 @SpringBootTest와 TestRestTemplate를 사용
 */
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    /**
     * TestRestTemplate
     * http요청 후 Json,xml, String과 같은 응답을 받을 수 있는 템플릿
     * ResponseEntity와 Server to Server통신하는데 자주 쓰인다.
     * Http request를 지원하는 HttpClient를 사용한다.
     *
     * ResponseEntity는 응답 처리시 값 뿐만 아니라 상태코드, 응답메세지 등을 포함하여 리턴 가능하다.(HttpEntity를 상속받기 때문에 HttpHeader와 body를 가질수 있다.
     */

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception{
        //given
        String title = "title";
        String content = "content";
        String pat = "cat";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("leehyunjong")
                .build();


        String url = "http://localhost:"+port+"/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        /**
         * RestTemplate를 통해 웹서버에 post요청을 보내는 부분
         *
         * restTemplate.postForEntity(url,요청값,반환 객체 타입)
         * POST method를 통해 요청을 보내고 결과로 ResponseEntity로 반환받는다.
         *
         * 통신메시지 관련 header와 body의 값들을 하나의 객체로 저장하는 것이 HttpEntity클래스 객체,
         * Request부분일때는 HttpEntity를 상속받은 RequestEntity가, Response부분일때는 HttpEntity를 상속받은 ResponseEntity가 받는다.
         *
         * title,content,author를 requestDto를 통해 해당 필드들을 포함한 생성자를 생성하고 url로 post method를 통해 controller->service->dao->domain으로 필드들을 저장한다음
         * 그 값들을 dto로 변환해서 long 타입으로 ResponseEntity값으로 반환해준다.
         *
         */

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정된다(){
        //given
        Posts savePosts = postsRepository.save(Posts.builder()
        .title("title")
        .content("content")
        .author("author")
        .build());

        Long updateId = savePosts.getId();
        String exceptedTitle = "title2";
        String exceptedContent = "content2";
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(exceptedTitle)
                .content(exceptedContent)
                .build();

        String url = "http://localhost:"+port+"/api/v1/posts/"+updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto); //requestDto객체를 HttpEntity를 인자로 넘겨 HttpEntity를 생성한다.

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,requestEntity,Long.class);
        /*
        restTemplate클래스의 exchange매소드를 통해 PUT메소드를 사용해준다.
        HttpEntity와 http메소드, url exchange에 담아서 보내면 설정해준 put메소드로 보내어 Controller에서 받을수있다.
         */

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);
        assertThat(responseEntity.getBody()).isEqualTo(updateId);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(exceptedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(exceptedContent);

    }
}
