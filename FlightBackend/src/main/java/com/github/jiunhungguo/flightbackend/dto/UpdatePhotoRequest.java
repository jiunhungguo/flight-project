package com.github.jiunhungguo.flightbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePhotoRequest {

    private String url;

    private String caption;

    private Long attractionId;
}
