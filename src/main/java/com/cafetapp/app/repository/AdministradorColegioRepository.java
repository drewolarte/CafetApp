package com.cafetapp.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cafetapp.app.entity.AdministradorColegio;

@Repository
public interface AdministradorColegioRepository extends CrudRepository<AdministradorColegio, String> {

}
