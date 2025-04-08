package com.github.jiunhungguo.flightbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.jiunhungguo.flightbackend.dto.CreatePhotoRequest;
import com.github.jiunhungguo.flightbackend.dto.PhotoResponse;
import com.github.jiunhungguo.flightbackend.dto.UpdatePhotoRequest;
import com.github.jiunhungguo.flightbackend.entity.Attraction;
import com.github.jiunhungguo.flightbackend.entity.Photo;
import com.github.jiunhungguo.flightbackend.repository.AttractionRepository;
import com.github.jiunhungguo.flightbackend.repository.PhotoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final AttractionRepository attractionRepository;

    @Override
    public PhotoResponse createPhoto(CreatePhotoRequest request) {
        Attraction attraction = attractionRepository.findById(request.getAttractionId())
                .orElseThrow(() -> new EntityNotFoundException("Attraction not found"));

        Photo photo = new Photo();
        photo.setAttraction(attraction);
        photo.setUrl(request.getUrl());
        photo.setCaption(request.getCaption());

        return mapToResponse(photoRepository.save(photo));
    }

    @Override
    public PhotoResponse updatePhoto(Long id, UpdatePhotoRequest request) {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Photo not found"));

        Attraction attraction = attractionRepository.findById(request.getAttractionId())
                .orElseThrow(() -> new EntityNotFoundException("Attraction not found"));

        photo.setAttraction(attraction);
        photo.setUrl(request.getUrl());
        photo.setCaption(request.getCaption());

        return mapToResponse(photoRepository.save(photo));
    }

    @Override
    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }

    @Override
    public List<PhotoResponse> getPhotosByAttractionId(Long attractionId) {
        return photoRepository.findByAttractionId(attractionId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PhotoResponse mapToResponse(Photo photo) {
        PhotoResponse response = new PhotoResponse();
        response.setId(photo.getId());
        response.setUrl(photo.getUrl());
        response.setCaption(photo.getCaption());
        response.setAttraction(photo.getAttraction().getName());

        return response;
    }

}
