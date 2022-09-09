package com.narsm.web.app.modules.main.endpoint.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.narsm.web.app.modules.account.domain.entity.Account;
import com.narsm.web.app.modules.account.support.CurrentUser;
import com.narsm.web.app.modules.study.domain.entity.Study;
import com.narsm.web.app.modules.study.infra.repository.StudyRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final StudyRepository studyRepository;

    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model) {
        if (account != null) {
            model.addAttribute(account);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/search/study")
    public String searchStudy(String keyword, Model model) {
        List<Study> studyList = studyRepository.findByKeyword(keyword);
        model.addAttribute(studyList);
        model.addAttribute("keyword", keyword);
        return "search";
    }
}
