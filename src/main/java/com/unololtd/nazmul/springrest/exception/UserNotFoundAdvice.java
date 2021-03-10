//package com.unololtd.nazmul.springrest.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@ControllerAdvice
//public class UserNotFoundAdvice extends ResponseEntityExceptionHandler {
//    @ResponseBody
//    @ExceptionHandler(UserNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    String userNotFoundHandler(UserNotFoundException ex) {
//        return ex.getMessage();
//    }
//
////    @ResponseBody
////    @ExceptionHandler(UserAlreadyExist.class)
////    @ResponseStatus(HttpStatus.CONFLICT)
////    String userAlreadyExist(UserAlreadyExist ex){
////        return  ex.getMessage();
////    }
//}
