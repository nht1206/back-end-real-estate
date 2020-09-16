package com.codegym.webservice.controller;

import com.codegym.dao.model.Comment;
import com.codegym.service.CommentService;
import com.codegym.webservice.payload.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/comments")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<Object> findAllComments(Pageable pageable) {
        return new ResponseEntity<>(commentService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findCommentById(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        if (comment == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this comment!"), HttpStatus.NOT_FOUND);
        } else  {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}/post")
    public ResponseEntity<Object> findCommentByPostId(@PathVariable Long id, Pageable pageable) {
        return new ResponseEntity<>(commentService.findByPostId(id, pageable), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> createComment(@RequestBody Comment comment) {
        commentService.save(comment);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(comment.getId()).toUri();
        return ResponseEntity.created(location)
                .body(comment);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        comment.setId(id);
        if (commentService.findById(id) == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this comment!"), HttpStatus.NOT_FOUND);
        }
        commentService.save(comment);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(comment.getId()).toUri();
        return ResponseEntity.created(location)
                .body(comment);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Delete repository successfully!"), HttpStatus.OK);
    }
}
