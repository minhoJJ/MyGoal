package com.narsm.web.module.account.endpoint.controller;

import lombok.Data;

@Data
public class SignUpForm {
    private String nickname;
    private String email;
    private String password;
}
