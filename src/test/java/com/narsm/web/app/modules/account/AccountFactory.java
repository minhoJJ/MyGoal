package com.narsm.web.app.modules.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.narsm.web.app.modules.account.domain.entity.Account;
import com.narsm.web.app.modules.account.infra.repository.AccountRepository;

@Component
public class AccountFactory {
    @Autowired AccountRepository accountRepository;

    public Account createAccount(String nickname) {
        return accountRepository.save(Account.with(nickname + "@example.com", nickname, "password"));
    }
}