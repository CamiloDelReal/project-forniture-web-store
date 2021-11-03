package com.example.productservice.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "picture_portrait_file_name")
    private String picturePortraitFileName;

    @Column(name = "picture_landscape_file_name")
    private String pictureLandscapeFileName;

    public Category(String name, String description, String picturePortraitFileName, String pictureLandscapeFileName) {
        this.name = name;
        this.description = description;
        this.picturePortraitFileName = picturePortraitFileName;
        this.pictureLandscapeFileName = pictureLandscapeFileName;
    }
}
