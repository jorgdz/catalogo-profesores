package com.hardcode.catalogoprofesores.dao;

import java.util.List;

import com.hardcode.catalogoprofesores.model.Profesor;

public interface ProfesorDao {
	
	void saveProfesor(Profesor profesor);
	
	List<Profesor>findAllProfesors();
	
	void deleteProfesorById(Long idProfesor);
	
	void updateProfesor(Profesor profesor);
	
	Profesor findById(Long idProfesor);
	
	Profesor findByName(String nombre);
	
}
