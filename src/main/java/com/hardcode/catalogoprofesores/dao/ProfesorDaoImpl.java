package com.hardcode.catalogoprofesores.dao;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hardcode.catalogoprofesores.model.Profesor;
import com.hardcode.catalogoprofesores.model.ProfesorRedesSociales;

@Repository
@Transactional
public class ProfesorDaoImpl extends AbstractSession implements ProfesorDao{
	
	public void saveProfesor(Profesor profesor) {
		getSession().persist(profesor);
	}

	public List<Profesor> findAllProfesors() {		
		return getSession().createQuery("from Profesor").list();
	}

	public void deleteProfesorById(Long idProfesor) {
		Profesor profesor = findById(idProfesor);
		if(profesor != null){
			
			Iterator<ProfesorRedesSociales> i=  profesor.getProfesorRedesSociales().iterator();
			
			while(i.hasNext()){
				ProfesorRedesSociales prs = i.next();
				i.remove();
				getSession().delete(prs);
			}
			
			profesor.getProfesorRedesSociales().clear();
			getSession().delete(profesor);
		}
	}

	public void updateProfesor(Profesor profesor) {
		getSession().update(profesor);
	}

	public Profesor findById(Long idProfesor) {		
		return (Profesor)getSession().get(Profesor.class, idProfesor);
	}

	public Profesor findByName(String nombre) {
		return (Profesor)getSession().createQuery(
				"from Profesor where nombre = :nombre")
				.setParameter("nombre",nombre).uniqueResult();
	}
	
}
