package com.cafetapp.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cafetapp.app.entity.SuperAdministrador;

@Repository
public interface SuperAdministradorRepository extends CrudRepository<SuperAdministrador, String> {

}
