package com.example.productservice.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryResponse {
    private Long id;
    private String name;
    private String pictureFileName;
}
