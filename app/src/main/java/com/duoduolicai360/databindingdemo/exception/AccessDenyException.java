package com.duoduolicai360.databindingdemo.exception;

/**
 * Created by chiclaim on 2016/02/25
 */
public class AccessDenyException extends RuntimeException {
    public AccessDenyException(String detailMessage) {
        super(detailMessage);
    }
}
