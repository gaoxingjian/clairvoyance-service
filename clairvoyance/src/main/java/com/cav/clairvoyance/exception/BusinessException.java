package com.cav.clairvoyance.exception;

import com.cav.clairvoyance.exception.code.ResponseCodeInterface;

public class BusinessException extends RuntimeException{

    private final int messageCode;

    private final String detailMessage;

    public BusinessException(int messageCode, String detailMessage) {
        super(detailMessage);
        this.messageCode = messageCode;
        this.detailMessage = detailMessage;
    }

    public BusinessException(ResponseCodeInterface responseCodeInterface) {
        this(responseCodeInterface.getCode(),responseCodeInterface.getMsg());
    }

    public int getMessageCode() {
        return messageCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
