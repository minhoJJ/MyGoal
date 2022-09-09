package com.narsm.web.module.event.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.narsm.web.module.account.domain.entity.Account;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Enrollment {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Event event;

  @ManyToOne
  private Account account;

  private LocalDateTime enrolledAt;

  private boolean accepted;

  private boolean attended;
}
