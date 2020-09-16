package com.codegym.webservice.controller;


import com.codegym.configuration.security.JwtUtils;
import com.codegym.dao.model.ERole;
import com.codegym.dao.model.Role;
import com.codegym.dao.model.User;
import com.codegym.service.RoleService;
import com.codegym.service.UserService;
import com.codegym.dao.model.CustomUserDetails;
import com.codegym.webservice.payload.response.ApiResponse;
import com.codegym.webservice.payload.request.SignInRequest;
import com.codegym.webservice.payload.request.SignUpRequest;
import com.codegym.webservice.payload.response.JwtResponse;
import com.codegym.webservice.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    AuthenticationManager authenticationManager;

    UserService userService;

    RoleService roleService;

    PasswordEncoder encoder;

    JwtUtils jwtUtils;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken((UserDetails) authentication.getPrincipal());

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Set<Role> roles = userDetails.getAuthorities().stream()
                .map(item -> roleService.findByRoleName(ERole.valueOf(item.getAuthority())))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getName(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getName(),
                signUpRequest.getEmail(),
                signUpRequest.getAddress(),
                signUpRequest.getPhoneNumber(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleService.findByRoleName(ERole.ROLE_USER);
            if (userRole == null) {
                throw new RuntimeException("Error: Role is not found.");
            }
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN);
                        if (adminRole == null) {
                            throw new RuntimeException("Error: Role is not found.");
                        }
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleService.findByRoleName(ERole.ROLE_USER);
                        if (userRole == null) {
                            throw new RuntimeException("Error: Role is not found.");
                        }
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userService.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false,"You are not signing!"));
        }
        User currentUser = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok().body(currentUser);
    }


}
