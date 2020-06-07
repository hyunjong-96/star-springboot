package com.hyunjong.book.springboot.domain.posts;

import com.hyunjong.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter //6
@NoArgsConstructor  //5
@Entity //1 //JPA의 어노테이션
public class Posts extends BaseTimeEntity {    //실제 DB의 테이블과 매칭될 클래스

    @Id //2
    @GeneratedValue(strategy = GenerationType.IDENTITY) //3
    private Long id;

    @Column(length = 500, nullable = false) //4
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder //7
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }//EntityManager가 활성화된 상태로 트랜잭션 안에서 DB의 데이터를 가져오면 가져온 데이터는 영속성 컨텍스트가 유지된다
    //이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영(이 상태로 Entity 객체의 값만 변경하면 별도로 Update쿼리를 날릴 필요가 없다)
    //이 개념은 더티체킹이라고한다.
}

/**
 * @Entity
 * 테이블과 링크될 클래스임을 나타낸다.
 * 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭합니다.
 * ex)SalesManager.java -> sales_manager table
 * Entity클래스에서는 Setter메소드를 만들지 않는다.
 *
 * @Id
 * 해당 테이블의 PK필드를 나타냅니다.
 *
 * @GeneratedValue
 * PK의 생성 규칙을 나타낸다
 * 스프링부터2.0에서는 GenerationType.IDENTITY 옵션을 추가해야 auto_increment가 된다.
 *
 * @Column
 * 테이블의 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 된다.
 * 사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용한다.
 * 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나(title) 타입을 TEXT로 변경하고싶거나(content) 등의 경우에 사용한다.
 *
 * @NoArgsConstructor
 * 기본생성자 자동 추가
 * public Posts(){}와 같은 효과
 *
 * @Getter
 * 클래스 내 모든 필드의 Getter 메소드를 자동생성
 *
 * @Builder
 * 해당 클래스의 빌더 패턴 클래스를 생성
 * 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
 * Builder method를 사용하게 되면 어느 필드에 어떤 값을 채워야할지 명확하게 인지할수 있다.
 * ex) Example.builder()
 *  .a(a)
 *  .b(b)
 *  .build()
 *
 * Entity 클래스를 Request/Response 클래스로 사용해서는 안된다
 * Entity클래스는 db와 맞닿은 핵심 클래스 이므로 Entity클래스를 기준으로 테이블이 생성되고, 스키마가 변경된다
 */
