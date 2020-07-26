package com.hyunjong.book.springboot.domain.user;


import com.hyunjong.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}

/*
@Enumerated(EnumType.String)
JPA로 DB에 저장할 떄 Enum값을 어떤 형태로 저장할지 결정한다.
기본적으로는 int로 된 숫자가 저장된다.
숫자로 저장하면 DB로 확인할때 그 값이 무슨 코드를 의미하는지 알수 없기 때문에 문자열(EnumType.STRING)로 저장할수 있도록 선언한다.
 */
