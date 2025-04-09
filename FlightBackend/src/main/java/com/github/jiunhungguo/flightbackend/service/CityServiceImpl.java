package com.github.jiunhungguo.flightbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.jiunhungguo.flightbackend.dto.CityResponse;
import com.github.jiunhungguo.flightbackend.dto.CreateCityRequest;
import com.github.jiunhungguo.flightbackend.dto.UpdateCityRequest;
import com.github.jiunhungguo.flightbackend.entity.City;
import com.github.jiunhungguo.flightbackend.repository.CityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<CityResponse> getAllCities() {
        return cityRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CityResponse getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
        return mapToResponse(city);
    }

    @Override
    public List<CityResponse> getCityByName(String name) {
        List<City> city = cityRepository.findByNameContaining(name);
        if (city.isEmpty()) {
            throw new RuntimeException("No cities found");
        }

        return city.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CityResponse createCity(CreateCityRequest request) {
        City city = new City();
        city.setName(request.getName());
        city.setCountry(request.getCountry());
        city.setLatitude(request.getLatitude());
        city.setLongitude(request.getLongitude());

        return mapToResponse(cityRepository.save(city));
    }

    @Override
    public CityResponse updateCity(Long id, UpdateCityRequest request) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));

        city.setName(request.getName());
        city.setCountry(request.getCountry());
        city.setLatitude(request.getLatitude());
        city.setLongitude(request.getLongitude());

        return mapToResponse(cityRepository.save(city));
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    private CityResponse mapToResponse(City city) {
        CityResponse response = new CityResponse();
        response.setId(city.getId());
        response.setName(city.getName());
        response.setCountry(city.getCountry());
        response.setLatitude(city.getLatitude());
        response.setLongitude(city.getLongitude());
        return response;
    }
}
