package com.hardcode.catalogoprofesores.service;

import java.util.List;

import com.hardcode.catalogoprofesores.model.ProfesorRedesSociales;
import com.hardcode.catalogoprofesores.model.RedesSociales;

public interface RedesSocialService {
	
	void saveRedesSociales(RedesSociales redes);
	
	List<RedesSociales>getAllRedes();
	
	void updateRedesSociales(RedesSociales redes);
	
	void deleteRedesSociales(int idRedes);
	
	RedesSociales findById(int idRedes);
	
	RedesSociales findByName(String nombre_red_social);
	
	ProfesorRedesSociales findByIdRedByIdAndNombre(int id_red_social, String NickName);
	
	ProfesorRedesSociales findRedesSocialByIdProfesorAndIdRedSocial(int idProfesor, int idRedSocial);
}
