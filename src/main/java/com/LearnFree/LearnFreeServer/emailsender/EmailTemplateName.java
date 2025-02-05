package com.LearnFree.LearnFreeServer.emailsender;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    VERIFY_EMAIL("verify_email"),

    ACCESS_NOTIFY("access_notify"),

    ACCESS_REMOVE("access_remove"),

    FORGET_PASSWORD_EMAIL("forget_password_email");

    private final String templateName;

    EmailTemplateName(String templateName){
        this.templateName=templateName;
    }

}
