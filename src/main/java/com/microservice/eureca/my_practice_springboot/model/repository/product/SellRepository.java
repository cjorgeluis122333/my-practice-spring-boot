package com.microservice.eureca.my_practice_springboot.model.repository.product;

import com.microservice.eureca.my_practice_springboot.model.entity.product.SellEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SellRepository extends CrudRepository<SellEntity, Long> {

    @Query("SELECT p.type.typeName, SUM(s.quantity) " +
            "FROM SellEntity s JOIN s.product p " +
            "WHERE s.sellDate BETWEEN :initDate AND :endDate " +
            "GROUP BY p.type.typeName")
    List<Object[]> getSellReportForProductType(
            @Param("initDate") LocalDateTime initDate,
            @Param("endDate") LocalDateTime endDate
    );

}
