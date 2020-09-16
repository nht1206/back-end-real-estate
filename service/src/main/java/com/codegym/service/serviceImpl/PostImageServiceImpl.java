package com.codegym.service.serviceImpl;

import com.codegym.dao.model.Post;
import com.codegym.dao.model.PostImage;
import com.codegym.dao.repository.PostImageRepository;
import com.codegym.service.PostImageService;
import com.codegym.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PostImageServiceImpl implements PostImageService {
    private PostImageRepository postImageRepository;

    @Autowired
    public void setPostImageRepository(PostImageRepository postImageRepository) {
        this.postImageRepository = postImageRepository;
    }

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Override
    public List<PostImage> findAll() {
        return postImageRepository.findAll();
    }

    @Override
    public PostImage findById(Long id) {
        return postImageRepository.findById(id).orElse(null);
    }

    @Override
    public Set<PostImage> findByPostId(Long id) {
        Post post = postService.findById(id);
        return post.getPostImages();
    }

    @Override
    public void save(PostImage postImage) {
        postImageRepository.save(postImage);
    }

    @Override
    public void deleteById(Long id) {
        postImageRepository.deleteById(id);
    }
}
