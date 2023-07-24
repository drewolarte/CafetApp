package com.cafetapp.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cafetapp.app.entity.Colegio;

@Repository
public interface ColegioRepository extends CrudRepository<Colegio, Long> {

}
