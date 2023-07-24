package com.cafetapp.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cafetapp.app.entity.Acudiente;

@Repository
public interface AcudienteRepository extends CrudRepository<Acudiente, Long> {

}
