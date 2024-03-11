package org.cibertec.edu.pe.repository;

import org.cibertec.edu.pe.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVentaDetalleRepository extends JpaRepository<DetalleVenta, Integer> {
}
