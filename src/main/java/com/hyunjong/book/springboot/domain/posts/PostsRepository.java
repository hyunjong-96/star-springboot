package com.hyunjong.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
}

/**
 * JPA에선 Repository라는 DB Layer접근자 이며 인터페이스로 생성한다.
 * 인터페이스를 생성 후, JpaRepository<Entity클래스, PK타입>를 상속하면 기본적인 CRUD 메소드가 자동으로 생성된다.
 * @Repository를 추가할 필요는 없지만 Entity클래스와 기본 Entity Repository는 함꼐 위치해야함.
 * Entity클래스는 기본 Repository없이는 제대로 역할을 할 수가 없다.
 * 나중에 프로젝트 규모가 커져 도메인별로 프로젝트를 분리해야 한다면 Entity클래스와 기본 Repository는 함꼐 움직여야 하므로 도메인 패키지에서 함께 관리한다.
 */
