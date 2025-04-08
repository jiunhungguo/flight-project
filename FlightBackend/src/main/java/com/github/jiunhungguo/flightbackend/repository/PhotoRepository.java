package com.github.jiunhungguo.flightbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.jiunhungguo.flightbackend.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findByAttractionId(Long attractionId);

}
