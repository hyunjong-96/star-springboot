package com.hyunjong.book.springboot.service;


import com.hyunjong.book.springboot.domain.posts.Posts;
import com.hyunjong.book.springboot.domain.posts.PostsRepository;
import com.hyunjong.book.springboot.web.dto.PostsListResponseDto;
import com.hyunjong.book.springboot.web.dto.PostsResponseDto;
import com.hyunjong.book.springboot.web.dto.PostsSaveRequestDto;
import com.hyunjong.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional  //트랜잭션 처리를 지원햐줌.
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" +id));//트랜잭션에서 DB에서 ID값의 데이터를 가져오고
        posts.update(requestDto.getTitle(),requestDto.getContent());//posts인스턴스를 통해 domain의 객체를 변경시켜준다 이 것을 더티체킹.

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" +id));
        //PostsResponseDto result = new PostsResponseDto(entity);
        //System.out.println(result);
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) //(readOnly = true)를 주면 트랜잭션 범위는 유지하되 수정,삭제, 기능이 없는 메소드에서 훨씬더 효과적으로 조회를 할수있는 서비스기능에만 사용한다.
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));
        postsRepository.delete(posts);
    }
}

/**
 * @RequiredArgsConstructor 어노테이션을 이용해 PostsRepository의 Bean을 주입받는다.
 * @AutoWired를 이용해 Bean객체를 받는 것은 권장하지 않는다.
 * 그럼 생성자를 직접 안 쓰고 롬복 어노테이션을 사용한 이유는 뭘까?
 * 그 이유는 해당 클래스의 의존성 관계가 변경될 떄마다 생성자 코드를 계속해서 수정하는 번거로움을 해결하기 위함이다.
 *
 * @Transactional
 * 트랜잭션은 db의 상태를 변환시키는 하나의 논리적 기능을 수행하기 위한 작업의 단위, 한꺼번에 모두 수행되어야 할 일련의 연산들.
 *
 * //postsRepository결과로 넘어온 Posts의 Stream을 map을 통해 PostListResponseDto로 변환
 * //.map(PostListResponseDto::new) == .map(posts -> new PostsListResponseDto(posts))
 * //map을 통해 PostListsResponseDto로 변환후 List로 변환해준다.
 *
 * stream() api를 통해 List를 받은것을 .collect(Collection.toString())을 통해 다시 List로 변경가능하다.
 */
