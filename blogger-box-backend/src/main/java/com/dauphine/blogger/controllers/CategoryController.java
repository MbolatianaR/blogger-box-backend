package com.dauphine.blogger.controllers;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/categories")

public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Category> getAll() {
        return service.getAll();
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
