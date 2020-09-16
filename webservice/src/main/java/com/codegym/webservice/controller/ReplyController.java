package com.codegym.webservice.controller;

import com.codegym.dao.model.Reply;
import com.codegym.service.ReplyService;
import com.codegym.webservice.payload.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/replies")
public class ReplyController {
    private ReplyService replyService;

    @Autowired
    public void setReplyService(ReplyService replyService) {
        this.replyService = replyService;
    }

    //-------------------Get All Replies--------------------------------------------------------

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> findAllReplies(Pageable pageable){
        return new ResponseEntity<>(replyService.findAll(pageable), HttpStatus.OK);
    }

    //-------------------Get One Reply By Id--------------------------------------------------------

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> findReplyById(@PathVariable("id")Long id){
        Reply reply = replyService.findById(id);
        if (reply == null){
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this reply!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reply,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/comment")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> finRepliesByCommentId(@PathVariable Long id, Pageable pageable) {
        return new ResponseEntity<>(replyService.findRepliesByComment(id, pageable), HttpStatus.OK);
    }

    //-------------------Create a Reply--------------------------------------------------------

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> createReply(@RequestBody Reply reply) {
        replyService.save(reply);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reply.getId()).toUri();
        return ResponseEntity.created(location)
                .body(reply);
    }

    //-------------------Update a Reply--------------------------------------------------------
    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> updateReply(@PathVariable Long id, @RequestBody Reply reply) {
        reply.setId(id);
        if (replyService.findById(id) == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this reply!"), HttpStatus.NOT_FOUND);
        }
        replyService.save(reply);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reply.getId()).toUri();
        return ResponseEntity.created(location)
                .body(reply);
    }

    //-------------------Delete a Reply--------------------------------------------------------

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> deleteReply(@PathVariable("id") Long id){
        Reply reply = replyService.findById(id);
        if (reply == null){
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this reply!"), HttpStatus.NOT_FOUND);
        }
        replyService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Delete reply successfully!"), HttpStatus.OK);
    }
}
