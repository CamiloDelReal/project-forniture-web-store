package com.example.productservice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @Column(name = "availability")
    private int availability;

    @Column(name = "picture_portrait_file_name")
    private String picturePortraitFileName;

    @Column(name = "picture_landscape_file_name")
    private String pictureLandscapeFileName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private List<Category> categories;

    public Product(String name, String description, float price, int availability, String picturePortraitFileName, String pictureLandscapeFileName, List<Category> categories) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.picturePortraitFileName = picturePortraitFileName;
        this.pictureLandscapeFileName = pictureLandscapeFileName;
        this.categories = categories;
    }
}
