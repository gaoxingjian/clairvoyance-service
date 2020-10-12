package com.cav.clairvoyance.utils;

import com.cav.clairvoyance.exception.code.BaseResponseCode;
import com.cav.clairvoyance.exception.code.ResponseCodeInterface;
import lombok.Data;

@Data
public class DataResult<T> {

    /**
     * 请求响应code，0为成功 其他为失败
     */
    private int code;
    /**
     * 响应异常码详细信息
     */
    private String msg;
    /**
     * 响应内容，code 0 时 返回 数据
     */
    private T data;

    public DataResult(int code, T data) {
        this.code = code;
        this.data = data;
        this.msg = null;

    }

    public DataResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DataResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public DataResult() {
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.msg = BaseResponseCode.SUCCESS.getMsg();
        this.data = null;
    }

    public DataResult(T data) {
        this.data = data;
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.msg = BaseResponseCode.SUCCESS.getMsg();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface) {
        this.data = null;
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface, T data) {
        this.data = data;
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }

    /**
     * 操作成功 data为null
     */
    public static <T> DataResult success() {
        return new <T>DataResult();

    }

    /**
     * 操作成功 data不为null
     */
    public static <T> DataResult success(T data) {
        return new <T>DataResult(data);
    }

    /**
     * 自定义 返回操作 data 可控
     */
    public static <T> DataResult getResult(int code, String msg, T data) {
        return new <T>DataResult(code, msg, data);
    }

    /**
     * 自定义 返回操作 data 为null
     */
    public static <T> DataResult getResult(int code, String msg) {
        return new <T>DataResult(code, msg);
    }

    /**
     * 自定义 返回操作 入参一般是异常code枚举 data 为空
     */
    public static <T> DataResult getResult(BaseResponseCode responseCode) {
        return new <T>DataResult(responseCode);
    }

    /**
     * 自定义 返回操作 入参一般是异常code枚举 data 可控
     */
    public static <T> DataResult getResult(BaseResponseCode responseCode, T data) {
        return new <T>DataResult(responseCode, data);
    }


}
