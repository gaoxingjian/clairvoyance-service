package com.cav.clairvoyance.exception.code;

public enum  BaseResponseCode implements ResponseCodeInterface {

    /**
     * 这个要和前段约定好
     * 引导用户去登录界面的
     * code=401001 引导用户重新登录
     * code=401002 token 过期刷新token
     * code=401008 无权限访问
     */
    SUCCESS(0,"Operation successful"),

    SYSTEM_BUSY(500001,"The system is busy, please try again later"),

    OPERATION_ERRO(500002,"Operation failed"),

    TOKEN_PARSE_ERROR(401001,"Login certificate has expired, please login again"),

    TOKEN_ERROR(401001,"Login certificate has expired, please login again"),

    ACCOUNT_ERROR(401001,"The account is abnormal, please contact the operator"),

    ACCOUNT_LOCK_ERROR(401001,"The user has been locked, please contact the operator"),

    TOKEN_PAST_DUE(401002,"The authorization information has expired. Please refresh the token"),

    DATA_ERROR(401003,"Abnormal incoming data"),

    NOT_ACCOUNT(401004,"The user does not exist, please register first"),

    HAS_ACCOUNT(401004,"This email has been registered, please change other email"),

    USER_LOCK(401005,"该The user has been locked, please contact the operator"),

    PASSWORD_ERROR(401006,"Wrong username or password"),

    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(401007,"Method parameter verification exception"),

    UNAUTHORIZED_ERROR(401008,"Authentication verification failed"),
    ;

    /**
     * 错误码
     */
    private final int code;
    /**
     * 错误消息
     */
    private final String msg;

    BaseResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
