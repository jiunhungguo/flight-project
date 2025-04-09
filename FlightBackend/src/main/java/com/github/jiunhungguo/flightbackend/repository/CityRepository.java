package com.github.jiunhungguo.flightbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.jiunhungguo.flightbackend.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByNameContaining(String name);

}
