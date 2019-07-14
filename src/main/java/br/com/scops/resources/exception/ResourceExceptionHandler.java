package br.com.scops.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.scops.servicies.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	//Vai receber a essenção e vai estorar se nao tiver
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
	       	StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

}
