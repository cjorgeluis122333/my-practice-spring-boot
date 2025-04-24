package com.microservice.eureca.my_practice_springboot.model.repository.product;

import com.microservice.eureca.my_practice_springboot.model.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    /**
     *
     * @return Quantity update rows(if return 0 the update failed)
     */
    @Modifying
    @Transactional
    @Query(
            "UPDATE ProductEntity p" +
                    " SET p.quantity = p.quantity - :productQuantity " +
                    "WHERE p.id = :idProducto AND p.quantity >= :productQuantity"
    )
    int updateQuantity(@Param("productId") Long productId, @Param("productQuantity") int productQuantity);


}
