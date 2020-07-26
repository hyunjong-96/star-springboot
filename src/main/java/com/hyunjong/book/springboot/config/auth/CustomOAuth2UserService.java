package com.hyunjong.book.springboot.config.auth;


import com.hyunjong.book.springboot.config.auth.dto.OAuthAttributes;
import com.hyunjong.book.springboot.config.auth.dto.SessionUser;
import com.hyunjong.book.springboot.domain.user.User;
import com.hyunjong.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
//CustemOAuth2UserService클래스는 구글 로그인 이후 가져온 사용자의 정보(email,name,picture)들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 지원한다.
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest, OAuth2User>
                delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();//1 - 현재 로그인 진행 중인 서비스를 구분하는 코드(google, naver, kakao ...)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();//2 - primary key

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());//3
        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));//4 - 세션에 사용자 정보를 저장하기 위한 Dto 클래스

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributekey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}

/*
(1) registrationID
: 현재 로그인 진행 중인 서비스를 구분하는 코드.(구글로그인인지 네이버로그인인지 구분을 위해 사용)

(2) userNameAttributeName
: OAuth2 로그인 진행 시 키가 되는 필드값을 이야기한다. primary key와 같은 의미.

(3) OAuthAttributes
: OAuth2UserService를 통해 가져온 OAuth2User 의 attribute를 담은 클래스입니다.

(4) SessionUser
: 세션에 사용자 정보를 저장하기 위한 Dto클래스.
 */
