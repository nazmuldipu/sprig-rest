package com.unololtd.nazmul.springrest.advice;

import com.unololtd.nazmul.springrest.exception.UserAlreadyExist;
import com.unololtd.nazmul.springrest.exception.UserNotFoundException;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class UserControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity notFoundException(final UserNotFoundException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity allReadyExistsException(final UserAlreadyExist e) {
        return error(e, HttpStatus.CONFLICT, e.getMessage());
    }

    private ResponseEntity error(
            final Exception exception, final HttpStatus httpStatus, final String logRef) {
        final String message =
                Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity(new VndErrors(logRef, message), httpStatus);
    }
}
