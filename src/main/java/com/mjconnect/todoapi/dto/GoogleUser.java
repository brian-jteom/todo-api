package com.mjconnect.todoapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleUser {

    private String sub; // Google 사용자 고유 ID
    private String email; // Google 이메일
    private String name; // 사용자 이름
    private String picture; // 프로필 이미지 URL
}
