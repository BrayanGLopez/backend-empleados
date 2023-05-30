package com.gestion.empleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.modelo.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{

}
