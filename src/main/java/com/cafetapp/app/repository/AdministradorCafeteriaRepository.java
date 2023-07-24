package com.cafetapp.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cafetapp.app.entity.AdministradorCafeteria;

@Repository
public interface AdministradorCafeteriaRepository extends CrudRepository<AdministradorCafeteria, String> {

}
