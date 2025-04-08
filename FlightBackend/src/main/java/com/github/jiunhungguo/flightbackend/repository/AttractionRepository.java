package com.github.jiunhungguo.flightbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.jiunhungguo.flightbackend.entity.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    List<Attraction> findByCityId(Long acityId);

}
