package com.microservice.eureca.my_practice_springboot.model.service.product;

import com.microservice.eureca.my_practice_springboot.model.entity.product.ProductEntity;
import com.microservice.eureca.my_practice_springboot.model.entity.product.SellEntity;
import com.microservice.eureca.my_practice_springboot.model.repository.product.ProductRepository;
import com.microservice.eureca.my_practice_springboot.model.repository.product.SellRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SellServiceImpl implements SellService {

    final private ProductRepository productRepository;
    final private SellRepository sellRepository;

    public SellServiceImpl(ProductRepository productRepository, SellRepository sellRepository) {
        this.productRepository = productRepository;
        this.sellRepository = sellRepository;
    }

    /**
     * Vende un producto actualizando su stock y registrando la venta en el historial.
     *
     * @param idProducto Identificador del producto a vender.
     * @param cantidad   Cantidad de producto a vender.
     * @throws Exception Si el stock es insuficiente o el producto no existe.
     */
    @Transactional
    @Override
    public void sellProductUsesCase(Long idProducto, int cantidad) throws Exception {
        // Actualiza el stock y verifica si fue posible hacerlo.
        int filasAfectadas = productRepository.updateQuantity(idProducto, cantidad);
        if (filasAfectadas == 0) {
            throw new Exception("No se pudo vender el product: stock insuficiente o product no encontrado.");
        }

        // Recupera el product actualizado
        Optional<ProductEntity> productOptional = productRepository.findById(idProducto);
        if (productOptional.isEmpty()) {
            throw new Exception("Producto no encontrado");
        }
        ProductEntity product = productOptional.get();

        // Crea el registro de sell en el historial
        SellEntity sell = new SellEntity();
        sell.setProduct(product);
        sell.setQuantity(cantidad);
        sell.setSellDate(LocalDateTime.now());

        // Guarda la sell en la base de datos
        sellRepository.save(sell);
    }
}

