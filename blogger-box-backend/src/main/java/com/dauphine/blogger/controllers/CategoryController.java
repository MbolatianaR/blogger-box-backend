package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CreationCategoryRequest;
import com.dauphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("v1/categories")

public class CategoryController {

    private final CategoryService service;
    private final CategoryService categoryService;

    public CategoryController(CategoryService service, CategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(
            summary = "Get all categories",
            description = "Retrieve all categories or filter like name"
    )
    public ResponseEntity<List<Category>> getAll(@RequestParam(required = false) String name) {
        List<Category> categories = name == null || name.isBlank()
                ? categoryService.getAll()
                : categoryService.getAllLikeName(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Get category by id",
            description = "Retrieve a category by id"
    )
    public ResponseEntity<Category> getById(@PathVariable UUID id) throws CategoryNotFoundByIdException {

        /*try {
            Category category = categoryService.getById(id);
            return ResponseEntity.ok(category);
        }
        catch (CategoryNotFoundByIdException e) {
            return ResponseEntity.notFound().build();
        }*/

        Category category = categoryService.getById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    @Operation(
            summary = "Create new category",
            description = "Create new category, only required field is the name of the category to create"
    )
    public ResponseEntity<Category> create(@RequestBody CreationCategoryRequest creationCategoryRequest) throws CategoryNameAlreadyExistsException {
        Category category = categoryService.create(creationCategoryRequest.getName());
        return ResponseEntity
                .created(URI.create("/v1/categories/" + category.getId()))
                .body(category);
    }

    @PutMapping("{id}")

    @Operation(
            summary = "Update category name",
            description = "Update the name of an existing category by providing the category id and the new name"
    )
    public ResponseEntity<Category> updateName(@PathVariable UUID id,
                                               @RequestBody String name) throws CategoryNameAlreadyExistsException, CategoryNotFoundByIdException {
        Category category = categoryService.updateName(id, name);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete category by id",
            description = "Deletes a category by its ID. Returns 204 if successful or 404 if not found."
    )
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) throws CategoryNotFoundByIdException {
        Optional<Category> category = service.getByIdOptional(id);
        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
