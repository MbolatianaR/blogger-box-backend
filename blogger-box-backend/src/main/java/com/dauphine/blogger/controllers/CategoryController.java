package com.dauphine.blogger.controllers;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    public List<Category> getAll(@RequestParam(required = false) String name) {
        List<Category> categories = name == null || name.isBlank()
                ? categoryService.getAll()
                : categoryService.getAllLikeName(name);
        return categories;
    }

    @GetMapping("{id}")
    public Category getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public Category create(String name) {
        return service.create(name);
    }

    @PutMapping("{id}")
    public Category updateName(@PathVariable UUID id,
                               @RequestBody String name) {
        return service.updateName(id, name);
    }

    @DeleteMapping
    public void deleteById(@PathVariable UUID id) {
       service.deleteById(id);
    }

}
