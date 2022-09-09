package com.narsm.web.app.modules.account.infra.predicates;

import java.util.Set;

import com.narsm.web.app.modules.account.domain.entity.QAccount;
import com.narsm.web.app.modules.tag.domain.entity.Tag;
import com.narsm.web.app.modules.zone.domain.entity.Zone;
import com.querydsl.core.types.Predicate;

public class AccountPredicates {
    public static Predicate findByTagsAndZones(Set<Tag> tags, Set<Zone> zones) {
        QAccount account = QAccount.account;
        return account.zones.any().in(zones).and(account.tags.any().in(tags));
    }
}
