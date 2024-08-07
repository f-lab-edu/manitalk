package com.example.web.service.auth;

import com.example.web.dto.CreateUserParam;
import com.example.web.service.UserService;
import com.example.web.vo.UserVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Value("${client.registration.naver.id}")
    private String naverRegistrationId;

    private final UserService userService;
    private final HttpServletRequest request;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> response = oAuth2User.getAttributes();
        if (userRequest.getClientRegistration().getRegistrationId().equals(naverRegistrationId)) {
            response = (Map<String, Object>) response.get("response");
        }

        login(userRequest, response);

        return oAuth2User;
    }

    private void login(OAuth2UserRequest userRequest, Map<String, Object> response) {
        String email = (String) response.get("email");
        UserVo userVo = userService.getUserByEmail(email);
        if (userVo == null) {
            CreateUserParam createUserParam = CreateUserParam.builder()
                    .email(response.get("email").toString())
                    .build();
            userVo = userService.createUser(createUserParam);
        }
        setSession(userRequest, userVo);
    }

    private void setSession(OAuth2UserRequest userRequest, UserVo userVo) {
        String accessToken = userRequest.getAccessToken().getTokenValue();
        String refreshToken = null;
        if (userRequest.getAdditionalParameters().containsKey("refresh_token")) {
            refreshToken = userRequest.getAdditionalParameters().get("refresh_token").toString();
        }

        HttpSession session = request.getSession();
        session.setAttribute("accessToken", accessToken);
        if (refreshToken != null) {
            session.setAttribute("refreshToken", refreshToken);
        }
        session.setAttribute("user", userVo);
    }
}