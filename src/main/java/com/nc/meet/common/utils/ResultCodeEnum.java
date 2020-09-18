package com.nc.meet.common.utils;

/**
 * 接口返回状态
 *
 * @author zigang
 */

public enum ResultCodeEnum {

    OK(200, "处理成功"),
    BAD_REQUEST(400, "请求参数有误"),
    UNAUTHORIZED(401, "未授权"),
    NOT_SUPPORTED(403, "请求方式不支持"),
    NOHANDLER_FOUND(404, "路径不存在，请检查路径是否正确"),
    PARAMS_MISS(483, "缺少接口中必填参数"),
    PARAM_ERROR(484, "参数非法"),
    FAILED_USER_ALREADY_EXIST(486, "该用户已存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");

    private int code;
    private String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
