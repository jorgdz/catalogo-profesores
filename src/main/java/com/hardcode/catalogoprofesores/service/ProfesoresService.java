package com.hardcode.catalogoprofesores.service;

import java.util.List;

import com.hardcode.catalogoprofesores.model.Profesor;

public interface ProfesoresService {
	
	void saveProfesor(Profesor profesor);
	
	List<Profesor>findAllProfesors();
	
	void deleteProfesorById(int idProfesor);
	
	void updateProfesor(Profesor profesor);
	
	Profesor findById(int idProfesor);
	
	Profesor findByName(String nombre);
	
}
