package com.dauphine.blogger.exceptions;

public class PostTitleAlreadyExistsException extends Exception {
    public PostTitleAlreadyExistsException(String title) {
        super("A post with the title '" + title + "' already exists.");
    }
}
