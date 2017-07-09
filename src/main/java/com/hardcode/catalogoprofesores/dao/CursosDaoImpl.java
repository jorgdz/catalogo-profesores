package com.hardcode.catalogoprofesores.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hardcode.catalogoprofesores.model.Cursos;

@Repository
@Transactional
public class CursosDaoImpl extends AbstractSession implements CursosDao{

	@Override
	public void saveCursos(Cursos curso) {
		getSession().persist(curso);		
	}

	@Override
	public List<Cursos> findAllCursos() {		
		return getSession().createQuery("from Cursos").list();
	}

	@Override
	public void deleteCursos(Long idCurso) {
		Cursos curso = findById(idCurso);
		if(curso != null){
			getSession().delete(curso);
		}
	}

	@Override
	public void updateCursos(Cursos curso) {		
		getSession().update(curso);
	}

	@Override
	public Cursos findById(Long idCurso) {	
		return (Cursos)getSession().get(Cursos.class,idCurso);
	}

	@Override
	public Cursos findByName(String nombre_curso) {
		return (Cursos)getSession().createQuery(
				"from Cursos where nombre_curso = :nombre_curso")
				.setParameter("nombre_curso",nombre_curso).uniqueResult();
	}
		
	@Override
	public List<Cursos> findByIdProfesor(Long id_profesor) {
		return (List<Cursos>)getSession().createQuery(
				"from Cursos c join c.profesor p where p.id_profesor = :id_profesor")
				.setParameter("id_profesor", id_profesor).list();
	}
	
}
