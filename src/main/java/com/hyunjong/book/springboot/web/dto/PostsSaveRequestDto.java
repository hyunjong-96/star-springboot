package com.hyunjong.book.springboot.web.dto;


import com.hyunjong.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}

/**
 * Request와 Response용 Dto는 View를 위한 클래스라 자주 변경이 필요하기 떄문에
 * Entity클래스를 건들지말고 Dto클래스를 변경해라.
 *
 * Entity클래스와 Controller에서 쓸 Dto는 분리해서 사용해야한다.
 */
