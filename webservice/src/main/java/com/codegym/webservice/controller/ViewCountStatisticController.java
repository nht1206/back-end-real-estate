package com.codegym.webservice.controller;

import com.codegym.dao.model.ViewCountStatistic;
import com.codegym.service.ViewCountStatisticService;
import com.codegym.webservice.payload.request.DateStatisticRequest;
import com.codegym.webservice.payload.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/viewCountStatistic")
public class ViewCountStatisticController {
    private ViewCountStatisticService viewCountStatisticService;

    @Autowired
    public void setViewCountStatisticService(ViewCountStatisticService viewCountStatisticService) {
        this.viewCountStatisticService = viewCountStatisticService;
    }

    //-------------------Get All ViewCountStatistic--------------------------------------------------------
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> findAllViewCountStatistic() {
        return new ResponseEntity<>(viewCountStatisticService.findAll(), HttpStatus.OK);
    }

    //-------------------Create a ViewCountStatistic--------------------------------------------------------
    @PostMapping
    public ResponseEntity<Object> createViewCountStatistic(@RequestBody ViewCountStatistic viewCountStatistic) {
        viewCountStatisticService.save(viewCountStatistic);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(viewCountStatistic.getId()).toUri();
        return ResponseEntity.created(location)
                .body(viewCountStatistic);
    }

    //-------------------Update a ViewCountStatistic by id--------------------------------------------------------
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateViewCountStatistic(@PathVariable Long id, @RequestBody ViewCountStatistic viewCountStatistic) {
        viewCountStatistic.setId(id);
        if (viewCountStatisticService.findById(id) == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this ViewCountStatistic!"), HttpStatus.NOT_FOUND);
        }
        viewCountStatisticService.save(viewCountStatistic);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(viewCountStatistic.getId()).toUri();
        return ResponseEntity.created(location)
                .body(viewCountStatistic);
    }

    //-------------------Get Last ViewCountStatistic--------------------------------------------------------
    @GetMapping(value = "/lastViewCountStatistic/{postId}")
    public ResponseEntity<Object> findLastViewCountStatistic(@PathVariable Long postId) {
        return new ResponseEntity<>(viewCountStatisticService.findLastViewCountStatistic(postId), HttpStatus.OK);
    }

    //-------------------Get List ViewCountStatistic--------------------------------------------------------
    @PostMapping(value = "/listViewCountStatistic")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getListViewCountStatistic(@RequestBody DateStatisticRequest date) {
        return new ResponseEntity<>(viewCountStatisticService.getListViewCountStatistic(date.getStartDay(), date.getEndDay()), HttpStatus.OK);
    }
}
