package com.hardcode.catalogoprofesores.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hardcode.catalogoprofesores.dao.CursosDao;
import com.hardcode.catalogoprofesores.model.Cursos;

@Service("cursosService")
@Transactional
public class CursosServiceImpl implements CursosService{
	
	@Autowired
	private CursosDao _cursodao;
	
	@Override
	public void saveCursos(Cursos curso) {
		_cursodao.saveCursos(curso);
	}

	@Override
	public List<Cursos> findAllCursos() {
		return _cursodao.findAllCursos();
	}

	@Override
	public void deleteCursos(int idCurso) {
		_cursodao.deleteCursos(idCurso);
	}

	@Override
	public void updateCursos(Cursos curso) {
		_cursodao.updateCursos(curso);
	}

	@Override
	public Cursos findById(int idCurso) {
		return _cursodao.findById(idCurso);
	}

	@Override
	public Cursos findByName(String nombre) {
		return _cursodao.findByName(nombre);
	}

	@Override
	public List<Cursos> findByIdProfesor(int idProfesor) {
		return _cursodao.findByIdProfesor(idProfesor);
	}

}
