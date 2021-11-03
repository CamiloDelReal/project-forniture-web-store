package com.example.productservice.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private float price;
    private int availability;
    private String picturePortraitFileName;
    private String pictureLandscapeFileName;
    private List<CategoryResponse> categories;
}
