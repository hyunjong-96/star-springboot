package com.hyunjong.book.springboot.domain.posts;

import com.hyunjong.book.springboot.domain.posts.Posts;
import com.hyunjong.book.springboot.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest //SpringBootTest를 사용할 경우 H2 데이터베이스를 자동으로 실행
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After  //단위테스트가 끝나면 테이블 posts를 전체 삭제시켜주어 데이터가 남아있어 다음 단위테스트에 오류 요소를 없애준다.
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
        .title(title)
        .content(content)
        .author("ahajongs@naver.com")
        .build());

        //when
        List<Posts> postsList = postsRepository.findAll();  //Posts생성자에 postsRepository에서 찾은 모든 객체를 넣어준다.
        /**
         * List컬렉션은 객체를 일렬로 늘어놓은 구조로 객체를 인덱스로 관리하기 떄문에 List컬렉션에 객체를 추가하면 자동 인덱스가 부여된다.
         * List컬렉션에서는 객체를 추가,검색,삭제 등의 메서드가 선언되어있다
         * Posts객체에는 @noArgsConstructor 어노테이션에 의해 자동으로 생성자가 생성되고
         * @Getter 어노테이션에 의해 get메소드가 선언되어있다.
         */


        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        /**
         * 그래서 postList의 객체 컬렉션의 첫번째 인덱스에 postsRepository에서 찾은 데이터들을
         * Posts의 변수로 할당해주고 Posts의 인스턴스객체에서 get메소드를 assertThat을 이용해 비교해준다.
         */

    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2020,6,7,8,0,0);
        postsRepository.save(Posts.builder()
        .title("title")
        .content("content")
        .author("leehyunjong")
        .build());

        //when
        List<Posts> postsLists = postsRepository.findAll();

        //then
        Posts posts = postsLists.get(0);
        System.out.println(">>>>>>>>>>>>>> createDate = "+posts.getCreateDate()+", modifiedDate = "+posts.getModifiedDate());

        assertThat(posts.getCreateDate().isAfter(now));
        assertThat(posts.getModifiedDate().isAfter(now));
    }
}

/**
 * @After
 * Junit에서 단위 테스트가 끝날 떄마다 수행되는 메소드를 지정
 * 보통은 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침법을 막기 위해 사용한다.
 * 여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행 시 테스트기ㅏ 실패할 수 있다.
 *
 * postsRepository.save
 * 테이블 posts에 insert/update 쿼리를 실행
 * id값이 있다면 update가 없다면 insert쿼리가 실행된다.
 *
 * postsRepository.findAll
 * 테이블 posts에 있는 모든 데이터를 조회해오는 메소드.
 */
