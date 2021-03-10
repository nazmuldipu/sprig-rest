package com.unololtd.nazmul.springrest.exception;

import org.springframework.security.acls.model.AlreadyExistsException;

public class UserAlreadyExist extends AlreadyExistsException {
    public UserAlreadyExist(String username) {
        super("User with username/phone = " + username + " already exists");
    }
//    UserAlreadyExist
}
