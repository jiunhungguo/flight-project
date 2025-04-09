package com.github.jiunhungguo.flightbackend.service;

import java.util.List;

import com.github.jiunhungguo.flightbackend.dto.CityResponse;
import com.github.jiunhungguo.flightbackend.dto.CreateCityRequest;
import com.github.jiunhungguo.flightbackend.dto.UpdateCityRequest;

public interface CityService {

    List<CityResponse> getAllCities();

    CityResponse getCityById(Long id);

    List<CityResponse> getCityByName(String name);

    CityResponse createCity(CreateCityRequest request);

    CityResponse updateCity(Long id, UpdateCityRequest request);

    void deleteCity(Long id);
}
