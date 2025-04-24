package com.microservice.eureca.my_practice_springboot.model.service.product;

public interface SellService {
   void sellProductUsesCase(Long idProducto, int cantidad) throws Exception;
}
