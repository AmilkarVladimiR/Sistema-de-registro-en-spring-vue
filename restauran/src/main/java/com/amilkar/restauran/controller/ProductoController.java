package com.amilkar.restauran.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.amilkar.restauran.model.ProductoModel;
import com.amilkar.restauran.repository.ProductoRepository;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos
    @GetMapping("/traer-productos")
    public List<ProductoModel> traerProductos() {
        return productoRepository.findAll();
    }

    // Obtener un solo producto por ID
    @GetMapping("/traer-producto/{id}")
    public ResponseEntity<ProductoModel> traerProducto(@PathVariable Long id) {
        Optional<ProductoModel> producto = productoRepository.findById(id);
        return producto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Insertar producto (validar si ya existe uno con el mismo nombre en la misma categoría)
    @PostMapping("/insertar-producto")
    public ResponseEntity<?> insertarProducto(@RequestBody ProductoModel producto) {
        boolean yaExiste = productoRepository.findAll().stream()
                .anyMatch(p -> p.getNombreProducto().equalsIgnoreCase(producto.getNombreProducto())
                        && p.getCategoria().equalsIgnoreCase(producto.getCategoria()));

        if (yaExiste) {
            return ResponseEntity.badRequest().body("Ya existe un producto con ese nombre en la misma categoría.");
        }

        ProductoModel productoGuardado = productoRepository.save(producto);
        return ResponseEntity.ok(productoGuardado);
    }

    // Actualizar un producto existente
    @PutMapping("/editar-producto/{id}")
    public ResponseEntity<ProductoModel> actualizarProducto(@PathVariable Long id, @RequestBody ProductoModel producto) {
        return productoRepository.findById(id).map(prodExistente -> {
            prodExistente.setNombreProducto(producto.getNombreProducto());
            prodExistente.setDescripcion(producto.getDescripcion());
            prodExistente.setPrecio(producto.getPrecio());
            prodExistente.setCategoria(producto.getCategoria());
            prodExistente.setImagenUrl(producto.getImagenUrl());
            ProductoModel productoActualizado = productoRepository.save(prodExistente);
            return ResponseEntity.ok(productoActualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar producto por ID
    @DeleteMapping("/eliminar-producto/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
    }
}
