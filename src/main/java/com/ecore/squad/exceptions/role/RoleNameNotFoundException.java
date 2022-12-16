package com.ecore.squad.exceptions.role;

import org.springframework.http.HttpStatus;

public class RoleNameNotFoundException extends RuntimeException {

    public static final int STATUS = HttpStatus.NOT_FOUND.value();
    public static final String MESSAGE = "Role name not found!";

    public RoleNameNotFoundException() {
        super(MESSAGE);
    }

}
