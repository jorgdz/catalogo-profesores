package com.hardcode.catalogoprofesores.dao;

import java.util.List;

import com.hardcode.catalogoprofesores.model.ProfesorRedesSociales;
import com.hardcode.catalogoprofesores.model.RedesSociales;

public interface RedesSocialesDao {
	
	void saveRedesSociales(RedesSociales redes);
	
	List<RedesSociales>getAllRedes();
	
	void updateRedesSociales(RedesSociales redes);
	
	void deleteRedesSociales(int idRedes);
	
	RedesSociales findById(int idRedes);
	
	RedesSociales findByName(String nombre_red_social);
	
	ProfesorRedesSociales findByIdRedByIdAndNombre(int id_red_social, String nick_name);
	
	ProfesorRedesSociales findRedesSocialByIdProfesorAndIdRedSocial(int idProfesor, int idRedSocial);
}
