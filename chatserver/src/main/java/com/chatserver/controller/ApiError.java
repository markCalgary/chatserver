package com.chatserver.controller;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiError {

	private LocalDateTime timestamp = LocalDateTime.now();
    //private HttpStatus status;
    private int status;
	private String message;
    private List<String> errors;
    private String path;
    
    public ApiError() {
        super();
    }

    public ApiError(final HttpStatus status, final String message, final List<String> errors, final String path) {
       super();
        this.status = status.value();
        this.message = message;
        this.errors = errors;
        this.path = path;
    }

    public ApiError(final HttpStatus status, final String message, final String error,  final String path) {
        super();
        this.status = status.value();
        this.message = message;
        this.errors = Arrays.asList(error);
        this.path = path;
    }

	    //

    public int getStatus() {
        return status;
    }

	    public void setStatus(final HttpStatus status) {
	        this.status = status.value();
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(final String message) {
	        this.message = message;
	    }

	    public List<String> getErrors() {
	        return errors;
	    }

	    public void setErrors(final List<String> errors) {
	        this.errors = errors;
	    }

	    public void setError(final String error) {
	        errors = Arrays.asList(error);
	    }

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public void setStatus(int status) {
			this.status = status;
		}

	    
}
