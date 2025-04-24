package com.microservice.eureca.my_practice_springboot.model.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@AllArgsConstructor
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @Column(name = "product_name")
    private String productName;
    @NotBlank
    private double price;
    @NotBlank
    private long quantity;

    private String description;
    @ManyToOne
    private ProductTypeEntity type;

    public ProductEntity() {
    }


    public ProductEntity(long id, String productName, double price, long quantity, String description, ProductTypeEntity type) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }
}
