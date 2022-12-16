package com.ecore.squad.errorHandler;

import com.ecore.squad.exceptions.role.RoleAlreadyExistException;
import com.ecore.squad.exceptions.role.RoleNameInvalidException;
import com.ecore.squad.exceptions.role.RoleNameNotFoundException;
import com.ecore.squad.exceptions.team.TeamDoesntExistException;
import com.ecore.squad.exceptions.user.UserDoesntBelongsToTheTeamException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(fieldMessages + "Field(s) validation error(s): " + fields, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        return new ResponseEntity<>("message: " + ex.getMessage() + " ClassName: ex.getClass().getName()", headers, status);
    }

    @ExceptionHandler({RoleAlreadyExistException.class, RoleNameInvalidException.class, RoleNameNotFoundException.class})
    public final ResponseEntity<Object> rolesException(RuntimeException ex) {
        if (ex.getClass().isNestmateOf(RoleAlreadyExistException.class)) {
            return new ResponseEntity<>(RoleAlreadyExistException.MESSAGE, HttpStatus.valueOf(RoleAlreadyExistException.STATUS));
        } else if (ex.getClass().isNestmateOf(RoleNameInvalidException.class)) {
            return new ResponseEntity<>(RoleNameInvalidException.MESSAGE, HttpStatus.valueOf(RoleNameInvalidException.STATUS));
        } else {
            return new ResponseEntity<>(RoleNameNotFoundException.MESSAGE, HttpStatus.valueOf(RoleNameNotFoundException.STATUS));
        }
    }

    @ExceptionHandler(TeamDoesntExistException.class)
    public final ResponseEntity<Object> teamDoesntExistException() {
            return new ResponseEntity<>(TeamDoesntExistException.MESSAGE, HttpStatus.valueOf(TeamDoesntExistException.STATUS));
    }

    @ExceptionHandler(UserDoesntBelongsToTheTeamException.class)
    public final ResponseEntity<Object> userDoesntBelongsToTheTeamException() {
        return new ResponseEntity<>(UserDoesntBelongsToTheTeamException.MESSAGE, HttpStatus.valueOf(UserDoesntBelongsToTheTeamException.STATUS));
    }

}
