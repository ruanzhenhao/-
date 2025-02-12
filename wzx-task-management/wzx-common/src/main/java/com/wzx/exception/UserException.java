package com.wzx.exception;

import org.apache.catalina.User;

public class UserException extends BaseException {
    public UserException() {
    }

    public UserException(String msg) {
        super(msg);
    }
}
