package com.wisps.auth.provider.exception;

import com.wisps.exception.ErrorCode;

/**
 * 认证错误码
 */
public enum AuthErrorCode implements ErrorCode {
    USER_STATUS_IS_NOT_ACTIVE("USER_STATUS_IS_NOT_ACTIVE", "用户状态不可用"),
    VERIFICATION_CODE_WRONG("VERIFICATION_CODE_WRONG", "验证码错误"),
    USER_QUERY_FAILED("USER_QUERY_FAILED", "用户信息查询失败"),
    USER_NOT_LOGIN("USER_NOT_LOGIN", "用户未登录"),
    USER_OPERATE_FAILED("USER_OPERATE_FAILED", "用户操作失败"),
    USER_NOT_EXIST("USER_NOT_EXIST", "用户不存在"),
    INVALID_PHONE_NO("INVALID_PHONE_NO", "手机号错误"),
    INVALID_MAIL("INVALID_MAIL", "邮箱错误"),
    INVALID_PARAMETER("INVALID_PARAMETER","invalid parameters"),
    SYSTEM_ERR("SYSTEM_ERR","system error"),
    CANNOT_RESOLVE_TOKEN("CANNOT_RESOLVE_TOKEN","cannot resolve token"),
    ;

    private String code;

    private String msg;

    AuthErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
