package com.gestion.empleados.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.empleados.repository.EmpleadoRepository;
import com.gestion.empleados.exceptions.ResourceNotFoundException;
import com.gestion.empleados.modelo.Empleado;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://brayanglopez.github.io/frontend-empleados/")
public class EmpleadoController {

	@Autowired
	private EmpleadoRepository repository;

	//Listar todos los empleados
	@GetMapping("/empleados")
	public List<Empleado> listarTodosLosEmpleados() {
		return repository.findAll();
	}
	
	//Guardar empleados nuevos
	@PostMapping("/empleados")
	public Empleado guardarEmpleado(@RequestBody Empleado empleado){
		return repository.save(empleado);
	}
	
	//Buscar un empleado por id
	
	@GetMapping("/empleados/{id}")
	public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id){
		Empleado empleado = repository.findById(id)
							.orElseThrow( () -> new ResourceNotFoundException("No existe empleado con ese Id: " + id));
		return ResponseEntity.ok(empleado);
	}
	
	@PutMapping("/empleados/{id}")
	public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoDet) {
		
		Empleado empleadoActualizado = new Empleado();
		Empleado empleado = repository.findById(id)
							.orElseThrow(() -> new ResourceNotFoundException("No existe empleado con ese Id: " + id));
		
		empleado.setNombre(empleadoDet.getNombre());
		empleado.setApellido(empleadoDet.getApellido());
		empleado.setEmail(empleadoDet.getEmail());
		
		empleadoActualizado = repository.save(empleado);
		
		return ResponseEntity.ok(empleadoActualizado);
	}
	
	//este metodo sirve para eliminar un empleado
	@DeleteMapping("/empleados/{id}")
	public ResponseEntity<Map<String,Boolean>> eliminarEmpleado(@PathVariable Long id){
		Empleado empleado = repository.findById(id)
				            .orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el ID : " + id));
	
		repository.delete(empleado);
		Map<String, Boolean> respuesta = new HashMap<>();
		respuesta.put("eliminar",Boolean.TRUE);
		return ResponseEntity.ok(respuesta);
	}
}
