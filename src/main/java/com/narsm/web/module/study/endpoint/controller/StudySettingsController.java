package com.narsm.web.module.study.endpoint.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.narsm.web.module.account.domain.entity.Account;
import com.narsm.web.module.account.support.CurrentUser;
import com.narsm.web.module.study.application.StudyService;
import com.narsm.web.module.study.domain.entity.Study;
import com.narsm.web.module.study.form.StudyDescriptionForm;
import com.narsm.web.module.study.infra.repository.StudyRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/study/{path}/settings")
@RequiredArgsConstructor
public class StudySettingsController {
    private final StudyRepository studyRepository;
    private final StudyService studyService;

    @GetMapping("/description")
    public String viewStudySetting(@CurrentUser Account account, @PathVariable String path, Model model) {
        Study study = studyService.getStudy(account, path);
        model.addAttribute(account);
        model.addAttribute(study);
        model.addAttribute(StudyDescriptionForm.builder()
                .shortDescription(study.getShortDescription())
                .fullDescription(study.getFullDescription())
                .build());
        return "study/settings/description";
    }

    @PostMapping("/description")
    public String updateStudy(@CurrentUser Account account, @PathVariable String path, @Valid StudyDescriptionForm studyDescriptionForm, Errors errors, Model model, RedirectAttributes attributes) {
        Study study = studyService.getStudy(account, path);
        if (errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(study);
            return "study/settings/description";
        }
        studyService.updateStudyDescription(study, studyDescriptionForm);
        attributes.addFlashAttribute("message", "스터디 소개를 수정했습니다.");
        return "redirect:/study/" + encode(path) + "/settings/description";
    }

    private String encode(String path) {
        return URLEncoder.encode(path, StandardCharsets.UTF_8);
    }
}