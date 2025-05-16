package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CreationPostRequest;
import com.dauphine.blogger.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.exceptions.PostTitleAlreadyExistsException;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @Operation(summary = "Get all posts", description = "Retrieve all posts or filter like content or title")
    public ResponseEntity<List<Post>> getAll(@RequestParam(required = false) String search) {
        List<Post> posts = search == null || search.isBlank()
                ? postService.getAll()
                : postService.getAllLikeTitleOrContent(search);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get post by id", description = "Retrieve a post by id")
    public ResponseEntity<Post> getById(@PathVariable UUID id) {
        Post post = postService.getById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    @PostMapping
    @Operation(summary = "Create new post", description = "Create new post with title, content and category")
    public ResponseEntity<Post> create(@RequestBody CreationPostRequest request) throws PostTitleAlreadyExistsException {
        Post post = postService.create(request.getTitle(), request.getContent(), request.getCategoryId());
        return ResponseEntity.created(URI.create("/v1/posts/" + post.getId())).body(post);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update post title", description = "Update the title and content of a post")
    public ResponseEntity<Post> update(@PathVariable UUID id, @RequestBody CreationPostRequest request)
            throws PostTitleAlreadyExistsException, PostNotFoundByIdException {
        Post post = postService.update(id, request.getTitle(), request.getContent());
        return ResponseEntity.ok(post);
    }

    @PutMapping("{id}/content")
    @Operation(summary = "Update post content", description = "Update the content of an existing post")
    public ResponseEntity<Post> updateContent(@PathVariable UUID id, @RequestBody String newContent)
            throws PostNotFoundByIdException {
        Post post = postService.updateContent(id, newContent);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete post by id", description = "Deletes a post by its ID")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) throws PostNotFoundByIdException {
        if (postService.existsById(id)) {
            postService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
