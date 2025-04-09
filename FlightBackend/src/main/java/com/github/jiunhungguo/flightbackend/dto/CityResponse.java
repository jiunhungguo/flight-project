package com.github.jiunhungguo.flightbackend.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityResponse {
    private Long id;
    private String name;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String image;
}
