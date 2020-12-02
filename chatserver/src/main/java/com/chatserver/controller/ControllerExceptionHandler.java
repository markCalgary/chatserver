package com.chatserver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.chatserver.model.SessionModel;
import com.chatserver.service.SessionService;

import javassist.NotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private SessionService aSessionService;
	
	@Autowired
	public ControllerExceptionHandler(SessionService inSessionService) {
		this.aSessionService = inSessionService;
	}
	
	@Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
	    String pathString = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
	    errors.add(ex.getLocalizedMessage()); 
	    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "ServletRequestBindingException", errors, pathString);
	    return handleExceptionInternal(ex, apiError, headers, HttpStatus.valueOf(apiError.getStatus()), request);
    }

	
	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
	    String pathString = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
	    errors.add(ex.getLocalizedMessage()); 
	    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "MissingServletRequestParameterException", errors, pathString);
	    return handleExceptionInternal(ex, apiError, headers, HttpStatus.valueOf(apiError.getStatus()), request);
    }
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    List<String> errors = new ArrayList<String>();
	    String pathString = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        errors.add(error.getField() + ": " + error.getDefaultMessage());
	    }
	    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	        errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	    }
	    
	    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "MethodArgumentNotValidException caught the following errors", errors, pathString);
	    return handleExceptionInternal(ex, apiError, headers, HttpStatus.valueOf(apiError.getStatus()), request);
	}
	
	@Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        //
        final List<String> errors = new ArrayList<String>();
	    String pathString = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "BindException: ", errors, pathString);
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.valueOf(apiError.getStatus()), request);
    }
	
	/*
	 * Custom Exception handling from this point forward typically errors thrown by the application
	 */
	
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex, final WebRequest request) {
        final List<String> errors = new ArrayList<String>();
        String pathString = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        errors.add(ex.getMessage());
	    final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "IllegalArgumentException", errors, pathString);
        return handleExceptionInternal(ex, apiError, null, HttpStatus.valueOf(apiError.getStatus()), request);
    }
	
	@ExceptionHandler(value=NotFoundException.class)
	protected ResponseEntity<Object> handleGenericNotFoundException(final NotFoundException ex, final WebRequest request) {
        final List<String> errors = new ArrayList<String>();
        String pathString = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        errors.add(ex.getMessage());
	    final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "NotFoundException", errors, pathString);
        return handleExceptionInternal(ex, apiError, null, HttpStatus.NOT_FOUND, request);
    }

	
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolationException(final DataIntegrityViolationException ex, final WebRequest request) {
        final List<String> errors = new ArrayList<String>();
        String pathString = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        errors.add(ex.getMessage());
	    final ApiError apiError = new ApiError(HttpStatus.CONFLICT, "DataIntegrityViolationException", errors, pathString);
        return handleExceptionInternal(ex, apiError, null, HttpStatus.CONFLICT, request);
    }
	
	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<Object> handleBadCredentialsException(final BadCredentialsException ex, final WebRequest request) {
        final List<String> errors = new ArrayList<String>();
        String pathString = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        errors.add(ex.getMessage());
	    final ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, "BadCredentialsException", errors, pathString);
        return handleExceptionInternal(ex, apiError, null, HttpStatus.FORBIDDEN, request);
    }
	
	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<Object> handleNullPointerException(final NullPointerException ex, final WebRequest request) {
        final List<String> errors = new ArrayList<String>();
        String pathString = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        errors.add(ex.toString());
        Map <String, String []> params = request.getParameterMap();
        String param = "";
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            param = param+entry.getKey() + ":" + (entry.getValue())[0]+" ";
        }
        String sessionId = request.getHeader("Authorization");
        if (sessionId != null) {
        	SessionModel aSession = this.aSessionService.getSession(sessionId);
        }
        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "NullPointerException: "+ex.toString(), errors, pathString);
        return handleExceptionInternal(ex, apiError, null, HttpStatus.CONFLICT, request);
    }
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, final WebRequest request) {
        final List<String> errors = new ArrayList<String>();
        String pathString = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        errors.add(ex.getMessage());
	    final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Exception", errors, pathString);

		//return new ResponseEntity<>(getBody(INTERNAL_SERVER_ERROR, ex, "Something Went Wrong"), new HttpHeaders(), INTERNAL_SERVER_ERROR);
		return handleExceptionInternal(ex, apiError, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
