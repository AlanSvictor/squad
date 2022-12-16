package com.ecore.squad.exceptions.user;

import org.springframework.http.HttpStatus;

public class UserDoesntBelongsToTheTeamException extends RuntimeException {

    public static final int STATUS = HttpStatus.BAD_REQUEST.value();
    public static final String MESSAGE = "User id does not belong to the team!";

    public UserDoesntBelongsToTheTeamException() {
        super(MESSAGE);
    }

}
