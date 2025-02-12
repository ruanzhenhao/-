package com.wzx.exception;

public class AccountRepeatException extends BaseException {
    public AccountRepeatException() {}

    public AccountRepeatException(String msg) {
        super(msg);
    }
}
