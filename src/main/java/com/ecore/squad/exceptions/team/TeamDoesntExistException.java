package com.ecore.squad.exceptions.team;

import org.springframework.http.HttpStatus;

public class TeamDoesntExistException extends RuntimeException {

    public static final int STATUS = HttpStatus.NOT_FOUND.value();
    public static final String MESSAGE = "Team does not exist!";

    public TeamDoesntExistException() {
        super(MESSAGE);
    }

}
