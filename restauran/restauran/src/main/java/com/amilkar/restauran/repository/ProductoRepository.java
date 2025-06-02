package com.amilkar.restauran.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amilkar.restauran.model.ProductoModel;

public interface ProductoRepository extends JpaRepository <ProductoModel, Long> {

}
