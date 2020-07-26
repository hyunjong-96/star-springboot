package com.hyunjong.book.springboot.config.auth;

import com.hyunjong.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor
@EnableWebSecurity//    1
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //security관련 클래스는 모두 이 곳에 담는다.
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable()//h2-console화면을 사용하기 위해 해당 옵션들을 disable 합니다.
                .and()
                .authorizeRequests()//url별 권한 관리를 설정하는 옵션의 시작점. authorizeRequests가 선언되어야만 antMatchers옵션을 사용할수 있다.
                    .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**","/fileUpload","/upload","/python/*").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()).anyRequest().authenticated()
                    //antMatchers는 권한 관리 대상을 지정하는 옵션
                    //url, HTTP 메소드별로 관리가 가능하다.
                    //지정된 url들은 permitALL() 옵션을 통해 전체 열람 권한을 주었다.
                    //"/api/v1/**"주소를 가진 API는 USER권한을 가진 사람만 가능하도록 했다.
                    //anyRequest().authenticated()옵션은 나머지 url들은 모두 인증된 사용자들(로그인한 사용자들)에게만 허용한다.
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()//Oauth로그인 성공 이후 사용자 정보를 가져올 떄의 설정들을 담당.
                            .userService(customOAuth2UserService);//소셜로그인 성공시 후속 조치를 진행할 UserService인터페이스의 구현체를 등록한다.

    }
}
/*
WebSecurityConfigurerAdapter
: 로그인할때 필요한 메소드들이 많이 있음

(1) @EnableWebSecurity
spring Security설정들을 활성화시켜 준다.

(2)
 */
