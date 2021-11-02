package com.example.productservice.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRequest {
    @NotNull
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Float price;

    @NotNull
    private Integer availability;

    private List<String> categories;
}
