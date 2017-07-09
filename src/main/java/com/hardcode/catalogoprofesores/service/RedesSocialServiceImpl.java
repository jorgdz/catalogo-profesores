package com.hardcode.catalogoprofesores.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hardcode.catalogoprofesores.dao.RedesSocialesDao;
import com.hardcode.catalogoprofesores.model.ProfesorRedesSociales;
import com.hardcode.catalogoprofesores.model.RedesSociales;

@Service("redesSocialService")
@Transactional
public class RedesSocialServiceImpl implements RedesSocialService{

	@Autowired
	private RedesSocialesDao _redesSocial;
	
	@Override
	public void saveRedesSociales(RedesSociales redes) {
		_redesSocial.saveRedesSociales(redes);
	}

	@Override
	public List<RedesSociales> getAllRedes() {	
		return _redesSocial.getAllRedes();
	}

	@Override
	public void updateRedesSociales(RedesSociales redes) {
		_redesSocial.updateRedesSociales(redes);
	}

	@Override
	public void deleteRedesSociales(Long idRedes) {
		_redesSocial.deleteRedesSociales(idRedes);
	}

	@Override
	public RedesSociales findById(Long idRedes) {	
		return _redesSocial.findById(idRedes);
	}

	@Override
	public RedesSociales findByName(String nombre_red_social) {	
		return _redesSocial.findByName(nombre_red_social);
	}

	@Override
	public ProfesorRedesSociales findByIdRedByIdAndNombre(Long id_red_social, String NickName) {
		return _redesSocial.findByIdRedByIdAndNombre(id_red_social, NickName);
	}

	@Override
	public ProfesorRedesSociales findRedesSocialByIdProfesorAndIdRedSocial(Long idProfesor, Long idRedSocial) {
		return _redesSocial.findRedesSocialByIdProfesorAndIdRedSocial(idProfesor, idRedSocial);
	}

}
