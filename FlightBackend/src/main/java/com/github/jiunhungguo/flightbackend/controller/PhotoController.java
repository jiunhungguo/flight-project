package com.github.jiunhungguo.flightbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jiunhungguo.flightbackend.dto.CreatePhotoRequest;
import com.github.jiunhungguo.flightbackend.dto.PhotoResponse;
import com.github.jiunhungguo.flightbackend.dto.UpdatePhotoRequest;
import com.github.jiunhungguo.flightbackend.service.PhotoService;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<PhotoResponse> createPhoto(@RequestBody CreatePhotoRequest request) {
        return ResponseEntity.ok(photoService.createPhoto(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhotoResponse> updatePhoto(@PathVariable Long id,
            @RequestBody UpdatePhotoRequest request) {
        return ResponseEntity.ok(photoService.updatePhoto(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/attraction/{attractionId}")
    public ResponseEntity<List<PhotoResponse>> getPhotosByAttractionId(@PathVariable Long attractionId) {
        List<PhotoResponse> responses = photoService.getPhotosByAttractionId(attractionId);
        return ResponseEntity.ok(responses);
    }
}