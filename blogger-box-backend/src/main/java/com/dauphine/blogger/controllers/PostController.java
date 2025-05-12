package com.dauphine.blogger.controllers;

import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public Post getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public Post create(@RequestParam String title,
                       @RequestParam String content,
                       @RequestParam(required = false) UUID categoryId) {
        if (categoryId != null) {
            return service.create(title, content, categoryId);
        } else {
            return service.create(title, content);
        }
    }

    @PutMapping("{id}")
    public Post update(@PathVariable UUID id,
                       @RequestBody Post updatedPost) {
        return service.update(id, updatedPost.getTitle(), updatedPost.getContent());
    }

    @PutMapping("{id}/content")
    public Post updateContent(@PathVariable UUID id,
                              @RequestBody String newContent) {
        return service.updateContent(id, newContent);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable UUID id) {
        service.deleteById(id);
    }
}