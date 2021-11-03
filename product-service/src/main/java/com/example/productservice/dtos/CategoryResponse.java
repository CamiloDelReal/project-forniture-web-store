package com.example.productservice.dtos;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String picturePortraitFileName;
    private String pictureLandscapeFileName;
}
