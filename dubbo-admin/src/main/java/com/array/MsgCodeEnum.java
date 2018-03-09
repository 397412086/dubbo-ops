package com.array;

/**
 * Created by cdf on 2017/9/21.
 * 错误码统一定义到这里
 */
public enum MsgCodeEnum {
    TOKEN_INVALID(-5,"token无效"),
    USER_NO_EXIST(-4, "会员不存在"),
    NO_LOGIN(-3,"未登录"),
    SERVER_ERROR(-2, "服务异常"),
    FAILURE(-1, "请求失败"), // 默认失败

    SUCCESS(0, "请求成功"), // 默认成功
    UI_INVALID_PARAM(1000, "界面传参错误"),   // 返回的错误信息例如：[cellphone]输入手机号码格式不对！;[password]密码格式错误，必须为6-16个字符（包含字母+数字）; 前端需要解析，然后提示用户
    INVALID_PARAM(1001, "参数错误"),
    USERNAME_OR_PASSWORD_ERROR(1004, "用户名或密码错误!账号{}已登录失败{}次，连续失败5次后将被锁定半小时！"),
    LOGIN_LOCK_REMAINING_TIME(1005, "账号{}锁定剩余时间{}分钟！"),
    USERNAME_FORBID_ERROR(1006, "账号被禁用！请联系管理员。");


    private int code;

    private String message;

    MsgCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
