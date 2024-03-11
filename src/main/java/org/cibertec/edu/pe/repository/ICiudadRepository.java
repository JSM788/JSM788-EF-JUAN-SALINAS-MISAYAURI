package org.cibertec.edu.pe.repository;

import org.cibertec.edu.pe.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICiudadRepository extends JpaRepository<Ciudad, String> {

}
