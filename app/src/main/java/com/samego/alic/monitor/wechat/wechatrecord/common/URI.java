package com.samego.alic.monitor.wechat.wechatrecord.common;

public class URI {
    // 基础域
    private static final String BASE_DOMAIN = "http://192.168.2.119:8081";
    // 账号数据同步URI
    public static final String URI_ACCOUNT_SYNC = BASE_DOMAIN + "/platform/chatRecord/sync";

    // 接口标识
    public static final int INTERFACE_SIGN_ACCOUNT = 1;
    public static final int INTERFACE_SIGN_CONTACT = 2;
}