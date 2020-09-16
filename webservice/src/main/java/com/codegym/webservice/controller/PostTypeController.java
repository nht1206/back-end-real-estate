package com.codegym.webservice.controller;

import com.codegym.dao.model.Post;
import com.codegym.dao.model.PostType;
import com.codegym.service.PostService;
import com.codegym.service.PostTypeService;
import com.codegym.webservice.payload.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/postTypes")
public class PostTypeController {
    private PostTypeService postTypeService;

    @Autowired
    public void setPostTypeService(PostTypeService postTypeService) {
        this.postTypeService = postTypeService;
    }

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public ResponseEntity<Object> findAllPostType() {
        return new ResponseEntity<>(postTypeService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/pages")
    public ResponseEntity<Object> findAllPostType(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(postTypeService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findPostTypeById(@PathVariable Long id) {
        PostType postType = postTypeService.findById(id);
        if (postType == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this post type!"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(postType, HttpStatus.OK);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createPostType(@RequestBody PostType postType) {
        boolean success = postTypeService.save(postType);
        if (!success) {
            return new ResponseEntity<>(new ApiResponse(false, "Cannot save post type!"), HttpStatus.BAD_REQUEST);
        }
        postTypeService.save(postType);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postType.getId()).toUri();
        return ResponseEntity.created(location)
                .body(postType);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updatePostType(@PathVariable Long id, @RequestBody PostType postType) {
        boolean success = postTypeService.save(postType);
        if (!success) {
            return new ResponseEntity<>(new ApiResponse(false, "Cannot save post type!"), HttpStatus.BAD_REQUEST);
        }
        postType.setId(id);
        if (postTypeService.findById(id) == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this post type!"), HttpStatus.NOT_FOUND);
        }
        postTypeService.save(postType);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postType.getId()).toUri();
        return ResponseEntity.created(location)
                .body(postType);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deletePostType(@PathVariable Long id) {
        PostType postType = postTypeService.findById(id);
        Iterable<Post> posts = postService.findByPostType_Id(id);

        if (postType == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find post type!"), HttpStatus.NOT_FOUND);
        }
        if (posts.iterator().hasNext()) {
            return new ResponseEntity<>(new ApiResponse(false, "Cannot delete this post type!"), HttpStatus.BAD_REQUEST);
        }
        postTypeService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Delete post type successfully!"), HttpStatus.OK);
    }
}
