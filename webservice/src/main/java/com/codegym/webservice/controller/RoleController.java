package com.codegym.webservice.controller;

import com.codegym.dao.model.Role;
import com.codegym.service.RoleService;
import com.codegym.webservice.payload.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/roles")
public class RoleController {
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    //-------------------Get All Roles--------------------------------------------------------

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> findAllRoles(){
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }


    //-------------------Get One Role By Id--------------------------------------------------------

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> findRoleById(@PathVariable("id")Long id){
        Role role = roleService.findById(id);
        if (role == null){
            return new ResponseEntity<>(new ApiResponse(false, "Can not find role!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(role,HttpStatus.OK);
    }

    //-------------------Create a Role--------------------------------------------------------

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createRole(@RequestBody Role role) {
        roleService.save(role);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId()).toUri();
        return ResponseEntity.created(location)
                .body(role);
    }

    //-------------------Update a Role--------------------------------------------------------
    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateRole(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        if (roleService.findById(id) == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this role!"), HttpStatus.NOT_FOUND);
        }
        roleService.save(role);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId()).toUri();
        return ResponseEntity.created(location)
                .body(role);
    }

    //-------------------Delete a Role--------------------------------------------------------

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteRole(@PathVariable("id") Long id){
        Role role = roleService.findById(id);
        if (role == null){
            return new ResponseEntity<>(new ApiResponse(false, "Can not find role!"), HttpStatus.NOT_FOUND);
        }
        roleService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Delete role successfully!"), HttpStatus.OK);
    }
}
