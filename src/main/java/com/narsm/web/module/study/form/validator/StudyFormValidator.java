package com.narsm.web.module.study.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.narsm.web.module.study.form.StudyForm;
import com.narsm.web.module.study.infra.repository.StudyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyFormValidator implements Validator {
    private final StudyRepository studyRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return StudyForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudyForm studyForm = (StudyForm) target;
        if (studyRepository.existsByPath(studyForm.getPath())) {
            errors.rejectValue("path", "wrong.path", "이미 사용중인 스터디 경로입니다.");
        }
    }
}