package com.hyunjong.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
/*
@Target
: 이 어노테이션이 생성될 수 있는 위치를 지정합니다.(.TYPE이므로 클래스,인터페이스,enum에 어노테이션을 선언할것이다)
PARAMETER로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있다.

@Retention
: 이 어노테이션이 언제부터 어디까지 영향을 영향을 유지할지 정한다.

@interface
: 이 파일을 어노테이션 클래스로 지정한다.(커스텀 어노테이션 선언)
LoginUser라는 이름을 가진 어노테이션이 생성되었다고 보면 된다.

리플렉션 : 어노테이션 정보를 분석하는 클래스
getAnnotation(), getAnnotations(), getDeclaredAnnotations()가 있습니다.
-> 객체를 통해 클래스의 정보를 분석하는 프로그래밍 기법


 */
/*
@Target

 - ElementType.TYPE : 클래스, 인터페이스, enum 선언부

 - ElementType.CONSTRUCTOR : 생성자 선언부

 - ElementType.LOCAL_VARIABLE : 지역 변수 선언부

 - ElementType.METHOD : 메소드 선언부

 - ElementType.PACKAGE : 패키지 선언부

 - ElementType.PARAMETER : 파라미터 선언부



@Retention

 - RetentionPolicy.RUNTIME : VM에서 유지 (리플렉션을 이용하여 검색 가능)

 - RetentionPolicy.SOURCE : 컴파일 시 class 파일에 추가되지 않는다.
 */
