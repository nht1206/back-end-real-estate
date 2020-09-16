package com.codegym.webservice.controller;

import com.codegym.dao.model.Category;
import com.codegym.dao.model.Post;
import com.codegym.service.CategoryService;
import com.codegym.webservice.payload.response.ApiResponse;
import com.codegym.service.PostService;
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
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public ResponseEntity<Object> findAllCategories() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/pages")
    public ResponseEntity<Object> findAllCategories(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(categoryService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findCategoryById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this category!"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createCategory(@RequestBody Category category) {
        boolean success = categoryService.save(category);
        if (!success) {
            return new ResponseEntity<>(new ApiResponse(false, "Cannot save category!"), HttpStatus.BAD_REQUEST);
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(location)
                .body(category);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        boolean success = categoryService.save(category);
        if (!success) {
            return new ResponseEntity<>(new ApiResponse(false, "Cannot save category!"), HttpStatus.BAD_REQUEST);
        }
        category.setId(id);
        if (categoryService.findById(id) == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this category!"), HttpStatus.NOT_FOUND);
        }
        categoryService.save(category);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(location)
                .body(category);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        Iterable<Post> posts = postService.findByCategory_Id(id);

        if (category == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this category!"), HttpStatus.NOT_FOUND);
        }
        if (posts.iterator().hasNext()) {
            return new ResponseEntity<>(new ApiResponse(false, "Cannot delete this reason!"), HttpStatus.BAD_REQUEST);
        }
        categoryService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Delete repository successfully!"), HttpStatus.OK);
    }


}
