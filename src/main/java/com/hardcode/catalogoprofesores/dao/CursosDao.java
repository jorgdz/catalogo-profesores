package com.hardcode.catalogoprofesores.dao;

import java.util.List;

import com.hardcode.catalogoprofesores.model.Cursos;

public interface CursosDao {
	
	void saveCursos(Cursos curso);
	
	List<Cursos>findAllCursos();
	
	void deleteCursos(int idCurso);
	
	void updateCursos(Cursos curso);
	
	Cursos findById(int idCurso);
	
	Cursos findByName(String nombre);
	
	List<Cursos>findByIdProfesor(int idProfesor);
}
