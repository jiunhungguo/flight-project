package com.github.jiunhungguo.flightbackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jiunhungguo.flightbackend.dto.CityResponse;
import com.github.jiunhungguo.flightbackend.dto.CreateCityRequest;
import com.github.jiunhungguo.flightbackend.dto.UpdateCityRequest;
import com.github.jiunhungguo.flightbackend.service.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<CityResponse> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/id/{id}")
    public CityResponse getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    @GetMapping("/city/{name}")
    public List<CityResponse> getCityByName(@PathVariable String name) {
        return cityService.getCityByName(name);
    }

    @PostMapping
    public CityResponse createCity(@RequestBody CreateCityRequest request) {
        return cityService.createCity(request);
    }

    @PutMapping("/{id}")
    public CityResponse updateCity(@PathVariable Long id, @RequestBody UpdateCityRequest request) {
        return cityService.updateCity(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}
