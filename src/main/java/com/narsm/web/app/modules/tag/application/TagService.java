package com.narsm.web.app.modules.tag.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.narsm.web.app.modules.tag.domain.entity.Tag;
import com.narsm.web.app.modules.tag.infra.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    public Tag findOrCreateNew(String tagTitle) {
        return tagRepository.findByTitle(tagTitle).orElseGet(
                () -> tagRepository.save(Tag.builder()
                        .title(tagTitle)
                        .build())
        );
    }
}