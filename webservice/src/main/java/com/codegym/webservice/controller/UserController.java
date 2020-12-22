package com.codegym.webservice.controller;

import com.codegym.dao.model.AccountReport;
import com.codegym.dao.model.Post;
import com.codegym.dao.model.User;
import com.codegym.service.PostService;
import com.codegym.service.UserService;
import com.codegym.webservice.payload.response.ApiResponse;
import com.codegym.webservice.payload.request.BlockUserRequest;
import com.codegym.webservice.payload.ChangePasswordToken;
import com.codegym.webservice.payload.request.UserSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    private PasswordEncoder encoder;

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    private final String NOT_FOUND_USER = "Cannot find this user!";


    //-------------------Get All Users--------------------------------------------------------

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> findAllUsers(Pageable pageable) {
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    //-------------------Get One User By Id--------------------------------------------------------


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> findUserById(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse(false, NOT_FOUND_USER), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //-------------------Create a User--------------------------------------------------------

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        userService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location)
                .body(user);
    }

    //-------------------Update User Profile--------------------------------------------------------

    @PatchMapping(value = "/{id}")
    @PreAuthorize("#user.getEmail() == #userDetails.getUsername()")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findById(id);

        if (currentUser == null) {
            return new ResponseEntity<>(new ApiResponse(false, NOT_FOUND_USER), HttpStatus.NOT_FOUND);
        }

        currentUser.setAvatar(user.getAvatar());
        currentUser.setName(user.getName());
        currentUser.setAddress(user.getAddress());
        currentUser.setPhoneNumber(user.getPhoneNumber());

        userService.save(currentUser);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(currentUser.getId()).toUri();
        return ResponseEntity.created(location)
                .body(currentUser);
    }

    //-------------------Change User's Password--------------------------------------------------------

    @PatchMapping("/{id}/changePassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> changePassword(@PathVariable Long id, @RequestBody ChangePasswordToken changePasswordToken) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(new ApiResponse(false, NOT_FOUND_USER), HttpStatus.NOT_FOUND);
        }

        if (!encoder.matches(changePasswordToken.getCurrentPassword(), user.getPassword())) {
            return new ResponseEntity<>(new ApiResponse(false, "The password is incorrect!"), HttpStatus.BAD_REQUEST);
        }
        user.setPassword(encoder.encode(changePasswordToken.getNewPassword()));
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //-------------------Delete a User--------------------------------------------------------

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse(false, NOT_FOUND_USER), HttpStatus.NOT_FOUND);
        }
        userService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Delete user successfully!"), HttpStatus.OK);
    }

    //-------------------Find Posts By UserId Pagination And Search--------------------------------------------------------

    @GetMapping("/{id}/posts")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> getPostsByUserId(@PathVariable("id") Long id, @PageableDefault(size = 5) Pageable pageable, @RequestParam("search") String search) {
        Page<Post> posts;
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse(false, NOT_FOUND_USER), HttpStatus.NOT_FOUND);
        }
        if (search != null) {
            posts = postService.findPostsByUser_IdAndTitleContaining(id, search, pageable);
        } else {
            posts = postService.findPostsByUserId(id, pageable);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @PostMapping("/{id}/block")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> blockUserById(@PathVariable Long id, @RequestBody BlockUserRequest blockUserRequest) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse(false, NOT_FOUND_USER), HttpStatus.NOT_FOUND);
        }
        AccountReport accountReport = new AccountReport();
        accountReport.setUser(user);
        accountReport.setReason(blockUserRequest.getReason());
        userService.blockById(id, accountReport);
        return ResponseEntity.ok().body(new ApiResponse(true, "Block account successfully!"));
    }

    @PostMapping("/{id}/unblock")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> unblockUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse(false, NOT_FOUND_USER), HttpStatus.NOT_FOUND);
        }
        userService.unblockById(id);
        return ResponseEntity.ok().body(new ApiResponse(true, "Unblock account successfully!"));
    }


    @PostMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> searchUser(@RequestBody UserSearchRequest userSearchRequest, @PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(userService.searchUser(pageable, userSearchRequest.getKeyword()), HttpStatus.OK);
    }
}
