package com.mjconnect.todoapi.service;

import com.mjconnect.todoapi.dto.GoogleUser;
import com.mjconnect.todoapi.entity.TbUser;
import com.mjconnect.todoapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GoogleUserService {

    @Autowired
    private UserRepository userRepository;

    public TbUser registerOrLoginGoogleUser(GoogleUser googleUser) {
        // 이메일을 username으로 저장 (이메일로 기존 사용자 검색)
        TbUser user = userRepository.findByUsername(googleUser.getSub());

        if (user == null) {
            // 사용자 존재하지 않으면 새로 등록
            user = new TbUser();
            user.setUsername(googleUser.getSub()); // Google 이메일을 username으로 사용
            user.setEnabled(true); // 활성화 상태로 설정
            user.setPassword("google"); // OAuth를 통해 가입한 경우, 비밀번호는 설정하지 않음
            user.setAuthority("USER"); // 기본 권한 설정 (필요에 따라 설정)
            userRepository.save(user);
//            log.info(user1.toString());
        }

        return user;
    }

    public GoogleUser getGoogleUserInfo(String accessToken) {
        // Google 사용자 정보를 요청할 URL
        String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;

        // RestTemplate을 사용하여 Google API로부터 사용자 정보를 요청
        RestTemplate restTemplate = new RestTemplate();

        // API 호출 및 응답 처리
        ResponseEntity<GoogleUser> response = restTemplate.getForEntity(url, GoogleUser.class);

        // GoogleUser 객체 반환
        return response.getBody();
    }
}
