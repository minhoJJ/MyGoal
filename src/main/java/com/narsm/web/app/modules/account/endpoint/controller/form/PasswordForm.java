package com.narsm.web.app.modules.account.endpoint.controller.form;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordForm {
    @Length(min = 8, max = 50)
    private String newPassword;
    @Length(min = 8, max = 50)
    private String newPasswordConfirm;
}
