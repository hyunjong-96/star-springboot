package com.hyunjong.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
/*
@MappedSuperclass
= JPA Entity클래스들이 BaseTimeEntity을 상속할 경우 필드들(CreateDate, modifiedDate)도 컬럼으로 인식하도록 한다.

@EntityListeners
= BaseTimeEntity클래스에 Auditing기능을 포함시킨다.
*Auditing이란 Spring Data JPA에서 시간에 대해 자동으로 값을 넣어주는 기능
영속성 컨텍스트에 저장하거나 조회를 수행한 후에 update를 하는 경우 audit를 이용해서 자동으로 시간을 매핑하여 DB테이블에 넣어주게 된다.

@CreatedDate
= Entity가 생성되어 저장될 때 시간이 자동 저장됩니다.

@LastModifiedDate
= 조회한 Entity의 값이 변경할 때 시간이 자동 저장된다.
 */
