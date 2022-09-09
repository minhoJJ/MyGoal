package com.narsm.web.app.modules.main.endpoint.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.narsm.web.app.infra.IntegrationTest;
import com.narsm.web.app.infra.mail.EmailService;
import com.narsm.web.app.modules.account.application.AccountService;
import com.narsm.web.app.modules.account.endpoint.controller.form.SignUpForm;
import com.narsm.web.app.modules.account.infra.repository.AccountRepository;

@IntegrationTest
class MainControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired AccountService accountService;
    @Autowired AccountRepository accountRepository;
    @MockBean EmailService emailService;

    @BeforeEach
    void beforeEach() {
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname("jaime");
        signUpForm.setEmail("lcalmsky@gmail.com");
        signUpForm.setPassword("test1234");
        accountService.signUp(signUpForm);
    }

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("이메일로 로그인: 성공")
    void login_with_email() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "lcalmsky@gmail.com")
                        .param("password", "test1234")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("jaime"));
    }

    @Test
    @DisplayName("닉네임으로 로그인: 성공")
    void login_with_nickname() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "jaime")
                        .param("password", "test1234")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("jaime"));
    }

    @Test
    @DisplayName("로그인: 실패")
    void login_fail() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "test")
                        .param("password", "test1234")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("로그아웃: 성공")
    void logout() throws Exception {
        mockMvc.perform(post("/logout")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated());
    }
}