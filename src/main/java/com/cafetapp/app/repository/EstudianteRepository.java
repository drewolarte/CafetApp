package com.cafetapp.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cafetapp.app.entity.Estudiante;

@Repository
public interface EstudianteRepository extends CrudRepository<Estudiante, String> {

}
