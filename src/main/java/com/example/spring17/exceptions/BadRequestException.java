package com.example.spring17.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.example.spring17.utils.Constants.BAD_REQUEST_FORMAT;
import static com.example.spring17.utils.Constants.BAD_REQUEST_TAKEN;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String param, String value, boolean isTaken) {
        super(param + StringUtils.SPACE + value
                + (isTaken ? BAD_REQUEST_TAKEN : BAD_REQUEST_FORMAT));
    }
}
