package com.dauphine.blogger.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalDefaultExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler({
            CategoryNotFoundByIdException.class,
            PostNotFoundByIdException.class
    })

    public ResponseEntity<String> handleNotFoundException (Exception ex) {
        logger.warn("[NOT FOUND] {}", ex.getMessage());
        return ResponseEntity
                .status(404)
                .body(ex.getMessage());
    }



}
