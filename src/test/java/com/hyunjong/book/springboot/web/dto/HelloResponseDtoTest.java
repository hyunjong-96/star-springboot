package com.hyunjong.book.springboot.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬북_기능_테스트(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name,amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}

/**
 * assertThat
 * assertj라는 테스트 검증 라이브러리의 검증 메소드
 * 검증받고싶은 대상을 메소드 인자로 받는다.
 * 메소드 체이닝이 지원되어 isEqualTo와 같은 메소드를 이어서 사용할수 있다.
 *
 * isEqualTo
 * assertj의 동등 비교 메소드이다.
 * assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 떄만 성공.
 *
 * Given
 * HelloResponseDto 클래스에 생성자로 주입될 값을 설정
 * 테스트 기반 환경을 구축하는 단계
 *
 * When
 * HelloResponseDto 클래스 생성 및 초기화
 *테스트 하고자 하는 행위 선언
 *
 * Then
 * 테스트의 결과를 검증
 * assertThat메서드를 통해 HelloResponseDto instance의 name,amount의 값을 확인
 * assertThat 성공으로 lombob의 @Getter을 통해 get메서드, @RequiredArgsContructor로 생성자가 자동으로 생성
 */
