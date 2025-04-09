package com.github.jiunhungguo.flightbackend.service;

import java.util.List;

import com.github.jiunhungguo.flightbackend.dto.AttractionResponse;
import com.github.jiunhungguo.flightbackend.dto.CreateAttractionRequest;
import com.github.jiunhungguo.flightbackend.dto.UpdateAttractionRequest;

public interface AttractionService {

    AttractionResponse createAttraction(CreateAttractionRequest request);

    AttractionResponse updateAttraction(Long id, UpdateAttractionRequest request);

    void deleteAttraction(Long id);

    List<AttractionResponse> getAttractionByName(String name);

    List<AttractionResponse> getAllAttractionsByCityId(Long cityId);
}
