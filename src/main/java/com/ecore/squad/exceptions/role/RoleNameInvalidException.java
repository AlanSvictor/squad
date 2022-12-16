package com.ecore.squad.exceptions.role;

import org.springframework.http.HttpStatus;

public class RoleNameInvalidException extends RuntimeException {

    public static final int STATUS = HttpStatus.BAD_REQUEST.value();
    public static final String MESSAGE = "Role name is invalid!";

    public RoleNameInvalidException() {
        super(MESSAGE);
    }

}
