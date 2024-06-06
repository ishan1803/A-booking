package com.experienceservice.ExperienceService.exceptions;

import com.experienceservice.ExperienceService.entity.CustomErrorObj;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Class - Global Exception Handler
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Method - Handling Resource Not Found Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorObj> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
        CustomErrorObj err = new CustomErrorObj();
        err.setStatusCode(HttpStatus.NOT_FOUND.value());
        err.setMessage(exception.getMessage());

        return new ResponseEntity<CustomErrorObj>(err, HttpStatus.NOT_FOUND);
    }

    // Method - Handling Bad Request Exception
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomErrorObj> handleBadRequestException(BadRequestException exception, WebRequest request){
        CustomErrorObj errorObj = new CustomErrorObj();
        errorObj.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObj.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorObj, HttpStatus.BAD_REQUEST);
    }

    // Method - Handling Forbidden Exception
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomErrorObj> handleForbiddenException(ForbiddenException exception, WebRequest request){
        CustomErrorObj err = new CustomErrorObj();
        err.setStatusCode(HttpStatus.FORBIDDEN.value());
        err.setMessage(exception.getMessage());
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    // Method - Handling Unautharized Exception
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomErrorObj> handleUnauthorizedException(UnauthorizedException exception, WebRequest request){
        CustomErrorObj err = new CustomErrorObj();
        err.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        err.setMessage(exception.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }

    // Method - Handling Internal Server Error Exception
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<CustomErrorObj> handleInternalServerErrorException(InternalServerErrorException exception, WebRequest request) {
        CustomErrorObj err = new CustomErrorObj();
        err.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        err.setMessage(exception.getMessage());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Method - Handling Method Argument Mismatch Exception
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorObj> handleMethodArgumentMismatchException(MethodArgumentTypeMismatchException exception, WebRequest request) {

        CustomErrorObj err = new CustomErrorObj();
        err.setStatusCode(HttpStatus.BAD_REQUEST.value());
        err.setMessage(exception.getMessage());

        return new ResponseEntity<CustomErrorObj>(err, HttpStatus.BAD_REQUEST);
    }

    // Method - Handling General Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorObj> handleGeneralException(Exception exception, WebRequest request) {

        CustomErrorObj err = new CustomErrorObj();
        err.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        err.setMessage(exception.getMessage());

        return new ResponseEntity<CustomErrorObj>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Method - Handling Method Argument Not Valid Exception
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, Object> body = new HashMap<String, Object>();

        body.put("statusCode", HttpStatus.BAD_REQUEST.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("messages", errors);

        return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
    }
}