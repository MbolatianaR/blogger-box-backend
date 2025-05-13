package com.dauphine.blogger.services;

import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {

    List<Category> getAll();

    Category getById(UUID id) throws CategoryNotFoundByIdException;

    Category create(String name);

    Category updateName(UUID id, String name) throws CategoryNotFoundByIdException;

    void deleteById(UUID id) throws CategoryNotFoundByIdException;

    List<Category> getAllLikeName(String name);

    Optional<Category> getByIdOptional(UUID id);

}
