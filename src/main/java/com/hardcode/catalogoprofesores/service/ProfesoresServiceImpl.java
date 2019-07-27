package com.hardcode.catalogoprofesores.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hardcode.catalogoprofesores.dao.ProfesorDao;
import com.hardcode.catalogoprofesores.model.Profesor;

@Service("profesoresService")
@Transactional
public class ProfesoresServiceImpl implements ProfesoresService{
	
	@Autowired
	private ProfesorDao _profesordao;
	
	@Override
	public void saveProfesor(Profesor profesor) {
		_profesordao.saveProfesor(profesor);
	}

	@Override
	public List<Profesor> findAllProfesors() {
		return _profesordao.findAllProfesors();
	}

	@Override
	public void deleteProfesorById(int idProfesor) {
		_profesordao.deleteProfesorById(idProfesor);
	}

	@Override
	public void updateProfesor(Profesor profesor) {
		_profesordao.updateProfesor(profesor);
	}

	@Override
	public Profesor findById(int idProfesor) {
		return _profesordao.findById(idProfesor);
	}

	@Override
	public Profesor findByName(String nombre) {
		return _profesordao.findByName(nombre);
	}

}
