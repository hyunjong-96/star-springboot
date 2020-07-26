package com.hyunjong.book.springboot.domain.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    //사용자의 권한을 관리한 Enum클래스 Role
    GUEST("ROLE_GUEST","손님"),
    USER("ROLE_USER","일반 사용자");

    private final String Key;
    private final String title;

}

/*
스프링 시큐리티에서 권한 코드에 항상 ROLE_ 이 앞으로 와야한다.
그러므로 ROLE_GUEST, ROLE_USER등으로 저장해야한다.
 */
