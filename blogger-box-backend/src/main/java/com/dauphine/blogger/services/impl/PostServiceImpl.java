package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.exceptions.PostTitleAlreadyExistsException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;

import com.dauphine.blogger.repositories.CategoryRepository;
import com.dauphine.blogger.repositories.PostRepository;
import com.dauphine.blogger.services.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    //private final List<Post> temporaryPosts;
    private final CategoryRepository categoryRepository;
    private final PostRepository repository;

    /*public PostServiceImpl() {
        this.temporaryPosts = new ArrayList<>();
        temporaryPosts.add(new Post(UUID.randomUUID(), "Title 1", "Content 1", new Category(UUID.randomUUID(), "Category 1")));
        temporaryPosts.add(new Post(UUID.randomUUID(), "Title 2", "Content 2", new Category(UUID.randomUUID(), "Category 2")));
        temporaryPosts.add(new Post(UUID.randomUUID(), "Title 3", "Content 3", new Category(UUID.randomUUID(), "Category 3")));
    }*/

    public PostServiceImpl(CategoryRepository categoryRepository, PostRepository repository) {
        this.categoryRepository = categoryRepository;
        this.repository = repository;
    }

    /*@Override
    public List<Post> getAllByCategoryId(UUID categoryId) {
        return List.of();
    }*/

    /*@Override
    public List<Post> getAll() {
        return temporaryPosts;
    }*/

    @Override
    public List<Post> getAllByCategoryId(UUID categoryId) {
        return repository.findAll().stream().filter(p -> p.getCategory().getId().equals(categoryId)).collect(Collectors.toList());
    }

    @Override
    public List<Post> getAll() {
        return repository.findAll();
    }

    /*@Override
    public Post getById(UUID id) {
        return temporaryPosts.stream()
                .filter(post -> id.equals(post.getId()))
                .findFirst()
                .orElse(null);
    }*/

    @Override
    public Post getById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    //@Override
    /*public Post create(String title, String content, UUID categoryId) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        return repository.save(post);
    }*/

    /*@Override
    public Post create(String title, String content, UUID categoryId) {
        return null;
    }*/

    /*@Override
    public Post create(String title, String content) {
        Post post = new Post(UUID.randomUUID(), title, content, new Category(UUID.randomUUID(), "Default Category"));
        temporaryPosts.add(post);
        return post;
    }*/

    @Override
    public Post create(String title, String content) throws PostTitleAlreadyExistsException {
        if (repository.existsByTitle(title)) {
            throw new PostTitleAlreadyExistsException(title);
        }

        Post post = new Post(UUID.randomUUID(), title, content, null);
        return repository.save(post);
    }

    @Override
    public Post update(UUID id, String title, String content) throws PostTitleAlreadyExistsException, PostNotFoundByIdException {
        Post post = repository.findById(id)
                .orElseThrow(PostNotFoundByIdException::new);

        boolean titleExists = repository.existsByTitle(title) && !post.getTitle().equals(title);
        if (titleExists) {
            throw new PostTitleAlreadyExistsException(title);
        }

        post.setTitle(title);
        post.setContent(content);

        return repository.save(post);
    }


    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }


    /*@Override
    public Post updateContent(UUID id, String newContent) {
        Post post = temporaryPosts.stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst()
                .orElse(null);
        if (post != null) {
            post.setContent(newContent);
        }
        return post;
    }*/

    @Override
    public Post updateContent(UUID id, String newContent) throws PostNotFoundByIdException {
        Post post = repository.findById(id)
                .orElseThrow(PostNotFoundByIdException::new);

        post.setContent(newContent);

        return repository.save(post);
    }

    /*@Override
    public void deleteById(UUID id) {
        temporaryPosts.removeIf(post -> id.equals(post.getId()));
    }*/

    @Override
    public void deleteById(UUID id) throws PostNotFoundByIdException {
        Post post = repository.findById(id)
                .orElseThrow(PostNotFoundByIdException::new);
        repository.delete(post);
    }

    @Override
    public List<Post> getAllLikeTitleOrContent(String search) {
        return repository.findAllLikeTitleOrContent(search);
    }

    @Override
    public Post create(String title, String content, UUID categoryId) throws PostTitleAlreadyExistsException {
        if (repository.existsByTitle(title)) {
            throw new PostTitleAlreadyExistsException(title);
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));

        Post post = new Post(UUID.randomUUID(), title, content, category);
        post.setCreatedDate(LocalDateTime.now());

        return repository.save(post);
    }

}