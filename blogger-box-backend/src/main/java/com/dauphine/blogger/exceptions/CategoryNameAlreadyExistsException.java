package com.dauphine.blogger.exceptions;

public class CategoryNameAlreadyExistsException extends RuntimeException {
    public CategoryNameAlreadyExistsException(String name) {
        super("A category with the name '" + name + "' already exists.");
    }
}
