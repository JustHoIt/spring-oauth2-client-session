package com.example.springoauth2clientsession.service;

import com.example.springoauth2clientsession.Model.Entity.UserEntity;
import com.example.springoauth2clientsession.Model.repository.UserRepository;
import com.example.springoauth2clientsession.dto.CustomOAuth2User;
import com.example.springoauth2clientsession.dto.GoogleResponse;
import com.example.springoauth2clientsession.dto.NaverResponse;
import com.example.springoauth2clientsession.dto.OAuth2Response;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    //DefaultOauth2UserService 는 Oauth2UserService의 구현체

    private final UserRepository userRepository;

    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser((oAuth2UserRequest));
        log.info(oAuth2User.getAttributes().toString());

        //로그인 서비스 회사명 받아오기
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderID();
        String role = null;

        UserEntity existData = userRepository.findByUsername(username);

        if (existData == null) {
            UserEntity userEntity = new UserEntity();

            userEntity.setUsername(username);
            userEntity.setEmail(oAuth2Response.getEmail());
            userEntity.setRole("ROLE_USER");
            userEntity.setOauth2name(registrationId);

            userRepository.save(userEntity);
        } else {
            //이미 로그인 한적이 없다면 업데이트
            existData.setUsername(username);
            existData.setEmail(oAuth2Response.getEmail());
            role = existData.getRole();

            userRepository.save(existData);
        }

        return new CustomOAuth2User(oAuth2Response, role);

    }
}
