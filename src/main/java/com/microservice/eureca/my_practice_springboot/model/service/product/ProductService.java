package com.microservice.eureca.my_practice_springboot.model.service.product;

import com.microservice.eureca.my_practice_springboot.model.entity.product.ProductEntity;

import java.util.Optional;

public interface ProductService {
    Optional<ProductEntity> sellProduct();

    Optional<ProductEntity> insertProduct(ProductEntity product);

    Optional<ProductEntity> updateProduct(ProductEntity product);


}
