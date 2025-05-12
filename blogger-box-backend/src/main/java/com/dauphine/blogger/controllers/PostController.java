package com.dauphine.blogger.controllers;

import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/posts")
public class PostController {

    private final PostService service;
    private final PostService postService;

    public PostController(PostService service, PostService postService) {
        this.service = service;
        this.postService = postService;
    }

    @GetMapping
    @Operation(
            summary = "Get all posts",
            description = "Retrieve all posts or filter like content or title"
    )

    public List<Post> getAll(@RequestParam(required = false)String search) {
        List<Post> posts = search == null || search.isBlank()
                ? postService.getAll()
                : postService.getAllLikeTitleOrContent(search);
        return posts;
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