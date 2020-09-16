package com.codegym.webservice.controller;

import com.codegym.dao.model.Post;
import com.codegym.dao.model.Region;
import com.codegym.service.PostService;
import com.codegym.service.RegionService;
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
@RequestMapping(path = "/api/v1/regions")
public class RegionController {
    private RegionService regionService;

    @Autowired
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public ResponseEntity<Object> findAllRegion() {
        return new ResponseEntity<>(regionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/pages")
    public ResponseEntity<Object> findAllRegion(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(regionService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findRegionById(@PathVariable Long id) {
        Region region = regionService.findById(id);
        if (region == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this region!"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(region, HttpStatus.OK);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> createRegion(@RequestBody Region region) {
        boolean success = regionService.save(region);
        if (!success) {
            return new ResponseEntity<>(new ApiResponse(false, "Cannot save region!"), HttpStatus.BAD_REQUEST);
        }
        regionService.save(region);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(region.getId()).toUri();
        return ResponseEntity.created(location)
                .body(region);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateRegion(@PathVariable Long id, @RequestBody Region region) {
        boolean success = regionService.save(region);
        if (!success) {
            return new ResponseEntity<>(new ApiResponse(false, "Cannot save region!"), HttpStatus.BAD_REQUEST);
        }
        region.setId(id);
        if (regionService.findById(id) == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find this region!"), HttpStatus.NOT_FOUND);
        }
        regionService.save(region);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(region.getId()).toUri();
        return ResponseEntity.created(location)
                .body(region);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteRegion(@PathVariable Long id) {
        Region region = regionService.findById(id);
        Iterable<Post> posts = postService.findByRegion_Id(id);

        if (region == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Can not find region!"), HttpStatus.NOT_FOUND);
        }
        if (posts.iterator().hasNext()) {
            return new ResponseEntity<>(new ApiResponse(false, "Cannot delete this region!"), HttpStatus.BAD_REQUEST);
        }
        regionService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Delete region successfully!"), HttpStatus.OK);
    }
}
