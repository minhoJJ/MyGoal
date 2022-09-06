package com.narsm.web.module.account.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Table(name = "persistent_logins")
@Entity
@Getter
public class PersistentLogins {
    @Id
    @Column(length = 64)
    private String series;

    @Column(length = 64)
    private String username;

    @Column(length = 64)
    private String token;

    @Column(name = "last_used", length = 64)
    private LocalDateTime lastUsed;

}
