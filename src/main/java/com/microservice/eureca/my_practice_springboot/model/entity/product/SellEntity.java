package com.microservice.eureca.my_practice_springboot.model.entity.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table
@Entity

@NoArgsConstructor
public class SellEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //Many SellEntity are associate to one ProductEntity
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private int quantity;

    private LocalDateTime sellDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getSellDate() {
        return sellDate;
    }

    public void setSellDate(LocalDateTime sellDate) {
        this.sellDate = sellDate;
    }

    @Override
    public String toString() {
        return "SellEntity{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", sellDate=" + sellDate +
                '}';
    }
}
