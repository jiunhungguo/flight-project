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

import com.github.jiunhungguo.flightbackend.dto.AttractionResponse;
import com.github.jiunhungguo.flightbackend.dto.CreateAttractionRequest;
import com.github.jiunhungguo.flightbackend.dto.UpdateAttractionRequest;
import com.github.jiunhungguo.flightbackend.service.AttractionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @PostMapping
    public ResponseEntity<AttractionResponse> create(@RequestBody CreateAttractionRequest request) {
        return ResponseEntity.ok(attractionService.createAttraction(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttractionResponse> update(@PathVariable Long id,
            @RequestBody UpdateAttractionRequest request) {
        return ResponseEntity.ok(attractionService.updateAttraction(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        attractionService.deleteAttraction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<AttractionResponse>> getAttractionByName(@PathVariable String name) {
        return ResponseEntity.ok(attractionService.getAttractionByName(name));
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<AttractionResponse>> getAllAttractionsByCityId(@PathVariable Long cityId) {
        return ResponseEntity.ok(attractionService.getAllAttractionsByCityId(cityId));
    }
}
