package com.narsm.web.module.study.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.narsm.web.module.account.domain.entity.Account;
import com.narsm.web.module.study.domain.entity.Study;
import com.narsm.web.module.study.domain.entity.StudyForm;
import com.narsm.web.module.study.infra.repository.StudyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyService {
    private final StudyRepository studyRepository;

    public Study createNewStudy(StudyForm studyForm, Account account) {
        Study study = Study.from(studyForm);
        study.addManager(account);
        return studyRepository.save(study);
    }
}
