package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.repositories.CategoryRepository;
import com.dauphine.blogger.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    /*private final List<Category> temporaryCategories;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.temporaryCategories = new ArrayList<>();
        temporaryCategories.add(new Category(UUID.randomUUID(), "my first category"));
        temporaryCategories.add(new Category(UUID.randomUUID(), "my second category"));
        temporaryCategories.add(new Category(UUID.randomUUID(), "my third category"));
    }*/

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    /*@Override
    public Category getById(UUID id) {
        return temporaryCategories.stream()
                .filter(category -> id.equals(category.getId()))
                .findFirst()
                .orElse(null);
    }*/

    @Override
    public Category getById(UUID id) {
        return repository.findById(id)
                .orElse(null);
    }

    /*@Override
    public Category create(String name) {
        Category category = new Category(UUID.randomUUID(), name);
        temporaryCategories.add(category);
        return category;
    }*/

    @Override
    public Category create(String name) {
        Category category = new Category(UUID.randomUUID(),name);
        return repository.save(category);
    }

    /*@Override
    public Category updateName(UUID id, String newName) {
        Category category = temporaryCategories.stream()
                .filter(c-> id.equals(c.getId()))
                .findFirst()
                .orElse(null);
        if (category != null) {
            category.setName(newName);
        }
        return category;
    }*/

    @Override
    public Category updateName(UUID id, String newName) {
        Category category = getById(id);
        if (category == null) {
            return null;
        }
        category.setName(newName);
        return repository.save(category);
    }

    /*@Override
    public void deleteById(UUID id) {
        temporaryCategories.removeIf(category -> id.equals(category.getId()));
    }*/

    @Override
    public boolean deleteById(UUID id) {
       repository.deleteById(id);
       return true;
    }

}
