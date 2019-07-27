package com.hardcode.catalogoprofesores.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hardcode.catalogoprofesores.model.ProfesorRedesSociales;
import com.hardcode.catalogoprofesores.model.RedesSociales;

@Repository
@Transactional
public class RedesSocialesDaoImpl extends AbstractSession implements RedesSocialesDao{

	@Override
	public void saveRedesSociales(RedesSociales redes) {
		getSession().persist(redes);
	}

	@Override
	public List<RedesSociales> getAllRedes() {		
		return getSession().createQuery("from RedesSociales").list();
	}

	@Override
	public void updateRedesSociales(RedesSociales redes) {
		getSession().update(redes);
	}

	@Override
	public void deleteRedesSociales(int idRedes) {
		RedesSociales rs = findById(idRedes);
		if(rs != null){
			getSession().delete(rs);
		}
	}

	@Override
	public RedesSociales findById(int idRedes) {
		return (RedesSociales)getSession().get(RedesSociales.class, idRedes);
	}

	@Override
	public RedesSociales findByName(String nombre_red_social) {
		return (RedesSociales) getSession().createQuery("from RedesSociales where nombre_red_social = :nombre_red_social")
				.setParameter("nombre_red_social", nombre_red_social).uniqueResult();
	}

	@Override
	public ProfesorRedesSociales findByIdRedByIdAndNombre(int id_red_social, String nick_name) {
		List<Object[]> objects = getSession().createQuery(
				"from ProfesorRedesSociales prs join prs.redes r where r.id_red_social = :id_red_social and prs.nick_name = :nick_name")
				.setParameter("id_red_social",id_red_social)
				.setParameter("nick_name", nick_name).list();
		if (objects.size() > 0) {
			for(Object[] objects2 : objects){
				for(Object object: objects2){
					if (object instanceof ProfesorRedesSociales) {
						return (ProfesorRedesSociales) object;
					}
				}
			}
		}
		
		return null;
	}

	
	//RELACIÓN DE RECURSOS	
	@Override
	public ProfesorRedesSociales findRedesSocialByIdProfesorAndIdRedSocial(int idProfesor, int idRedSocial) {
		List<Object[]> objects = getSession().createQuery(
				"from ProfesorRedesSociales prs join prs.redes rs join prs.profesor p where rs.id_red_social= :id_red_social and p.id_profesor = :id_profesor")
				.setParameter("id_red_social",idRedSocial)
				.setParameter("id_profesor", idProfesor).list();
		if (objects.size()>0) {
			for(Object[] objects2 : objects){
				for(Object object: objects2){
					if (object instanceof ProfesorRedesSociales) {
						return (ProfesorRedesSociales) object;
					}
				}
			}
		}
		
		return null;
	}

	
	
}
