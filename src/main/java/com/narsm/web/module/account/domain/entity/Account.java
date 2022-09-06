package com.narsm.web.module.account.domain.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.narsm.web.module.account.domain.support.ListStringConverter;
import com.narsm.web.module.domain.entity.AuditingEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder @Getter @ToString
public class Account extends AuditingEntity {

	@Id @GeneratedValue
	@Column(name = "account_id")
	private Long id;
	
	@Column(unique = true)                                                                              // (3)
    private String email;

    @Column(unique = true)                                                                              // (3)
    private String nickname;

    private String password;

    private boolean isValid;

    private String emailToken;

    @Embedded                                                                                           // (4)
    private Profile profile;

    @Embedded                                                                                           // (4)
    private NotificationSetting notificationSetting;

    @Embeddable                                                                                         // (5)
    @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder @Getter @ToString
    public static class Profile {
        private String bio;
        @Convert(converter = ListStringConverter.class)                                                 // (6)
        private List<String> url;
        private String job;
        private String location;
        private String company;
        @Lob @Basic(fetch = FetchType.EAGER)
        private String image;
    }

    @Embeddable                                                                                         // (5)
    @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder @Getter @ToString
    public static class NotificationSetting {
        private boolean studyCreatedByEmail;
        private boolean studyCreatedByWeb;
        private boolean studyRegistrationResultByEmailByEmail;
        private boolean studyRegistrationResultByEmailByWeb;
        private boolean studyUpdatedByEmail;
        private boolean studyUpdatedByWeb;
    }

}
