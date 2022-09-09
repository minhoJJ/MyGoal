package com.narsm.web.app.modules.study.infra.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.narsm.web.app.modules.study.domain.entity.Study;

@Transactional(readOnly = true)
public interface StudyRepositoryExtension {
    List<Study> findByKeyword(String keyword);
}
