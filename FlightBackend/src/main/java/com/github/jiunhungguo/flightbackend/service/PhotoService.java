package com.github.jiunhungguo.flightbackend.service;

import java.util.List;

import com.github.jiunhungguo.flightbackend.dto.CreatePhotoRequest;
import com.github.jiunhungguo.flightbackend.dto.PhotoResponse;
import com.github.jiunhungguo.flightbackend.dto.UpdatePhotoRequest;

public interface PhotoService {
    PhotoResponse createPhoto(CreatePhotoRequest request);

    PhotoResponse updatePhoto(Long id, UpdatePhotoRequest request);

    void deletePhoto(Long id);

    List<PhotoResponse> getPhotosByAttractionId(Long attractionID);

}
