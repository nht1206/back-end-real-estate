package com.codegym.webservice.controller;

import com.codegym.dao.model.Direction;
import com.codegym.service.DirectionService;
import com.codegym.webservice.payload.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/directions")
public class DirectionController {
    private DirectionService directionService;

    @Autowired
    public void setDirectionService(DirectionService directionService) {
        this.directionService = directionService;
    }

    @GetMapping
    public ResponseEntity<Object> findAllDirections() {
        return new ResponseEntity<>(directionService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findDirectionById(@PathVariable Long id) {
        Direction direction = directionService.findById(id);
        if (direction == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find direction!"), HttpStatus.BAD_REQUEST);
        } else  {
            return new ResponseEntity<>(direction, HttpStatus.OK);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createDirection(@RequestBody Direction direction) {
        directionService.save(direction);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(direction.getId()).toUri();
        return ResponseEntity.created(location)
                .body(direction);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateDirection(@PathVariable Long id, @RequestBody Direction direction) {
        direction.setId(id);
        if (directionService.findById(id) == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this direction!"), HttpStatus.NOT_FOUND);
        }
        directionService.save(direction);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(direction.getId()).toUri();
        return ResponseEntity.created(location)
                .body(direction);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteDirection(@PathVariable Long id) {
        directionService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Delete direction successfully!"), HttpStatus.OK);
    }

}
