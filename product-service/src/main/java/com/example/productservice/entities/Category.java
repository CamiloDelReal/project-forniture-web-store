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

    @Column(name = "picture_file_name")
    private String pictureFileName;

    public Category(String name, String pictureFileName) {
        this.name = name;
        this.pictureFileName = pictureFileName;
    }
}
