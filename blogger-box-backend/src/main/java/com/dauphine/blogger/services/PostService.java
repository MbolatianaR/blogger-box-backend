package com.dauphine.blogger.services;

import com.dauphine.blogger.models.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getAllByCategoryId(UUID categoryId);

    List<Post> getAll();

    Post getById(UUID id);

    Post create(String title, String content, UUID categoryId);

    Post update(UUID id, String title, String content);

    boolean existsById(UUID id);

    Post create(String title, String content);

    Post updateContent(UUID id, String newContent);

    void deleteById(UUID id);

    List<Post> getAllLikeTitleOrContent(String title);
}
