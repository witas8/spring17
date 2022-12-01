package com.example.spring17.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.example.spring17.utils.Constants.NOT_FOUND_REQUEST_BEGINNING;
import static com.example.spring17.utils.Constants.NOT_FOUND_REQUEST_ENDING;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String who, String param, String value) {
        super(who + NOT_FOUND_REQUEST_BEGINNING + param
                + StringUtils.SPACE + value + NOT_FOUND_REQUEST_ENDING);
    }

}
