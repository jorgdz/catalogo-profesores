package com.hardcode.catalogoprofesores.service;

import java.util.List;

import com.hardcode.catalogoprofesores.model.Cursos;

public interface CursosService {

	void saveCursos(Cursos curso);
	
	List<Cursos>findAllCursos();
	
	void deleteCursos(Long idCurso);
	
	void updateCursos(Cursos curso);
	
	Cursos findById(Long idCurso);
	
	Cursos findByName(String nombre);
	
	List<Cursos>findByIdProfesor(Long idProfesor);
}
