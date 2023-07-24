package com.cafetapp.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cafetapp.app.entity.HistorialCompras;

@Repository
public interface HistorialComprasRepository extends CrudRepository<HistorialCompras, Long> {

}
