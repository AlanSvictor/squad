package com.ecore.squad.exceptions.role;

import org.springframework.http.HttpStatus;

public class RoleAlreadyExistException extends RuntimeException {

    public static final int STATUS = HttpStatus.CONFLICT.value();
    public static final String MESSAGE = "Role already exist!";

    public RoleAlreadyExistException() {
        super(MESSAGE);
    }

}
