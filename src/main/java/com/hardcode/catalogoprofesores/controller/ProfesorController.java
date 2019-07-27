package com.hardcode.catalogoprofesores.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.hardcode.catalogoprofesores.model.Profesor;
import com.hardcode.catalogoprofesores.model.ProfesorRedesSociales;
import com.hardcode.catalogoprofesores.model.RedesSociales;
import com.hardcode.catalogoprofesores.service.ProfesoresService;
import com.hardcode.catalogoprofesores.service.RedesSocialService;
import com.hardcode.catalogoprofesores.util.CustomErrorType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1")
@CrossOrigin
public class ProfesorController {
	
	@Autowired
	private ProfesoresService _profesor;
	
	@Autowired
	private RedesSocialService _redes;
	
	//GET
	@RequestMapping(value="/profesores", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity <List<Profesor>> getAllProfesores(@RequestParam(value="name", required=false) String name){
		List<Profesor> profesores = new ArrayList<>();
		
		if (name == null) {
			profesores = _profesor.findAllProfesors();
			if(profesores.isEmpty()){
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Profesor>>(profesores, HttpStatus.OK);
		}else{
			Profesor profe = _profesor.findByName(name);
			if(profe == null){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			profesores.add(profe);
			return new ResponseEntity<List<Profesor>>(profesores, HttpStatus.OK);
		}
		
	}
	
	//GET ID
	@RequestMapping(value="/profesores/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Profesor>getProfesoresById(@PathVariable("id") int idProfesores){
		if(idProfesores <= 0){
			return new ResponseEntity(new CustomErrorType("El Id de los profesores no es correcto"), HttpStatus.CONFLICT);
		}
		Profesor profe = _profesor.findById(idProfesores);
		if(profe == null){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Profesor>(profe,HttpStatus.OK);
	}
	
	//POST
	@RequestMapping(value="/profesores", method=RequestMethod.POST, headers="Accept=application/json")
	public ResponseEntity<?> createProfesor(@RequestBody Profesor profesor, UriComponentsBuilder uriComponentBuilder){
			if(profesor.getNombre().equals(null) || profesor.getNombre().isEmpty()){
				return new ResponseEntity(new CustomErrorType("El nombre del profesor es requerido!!"), HttpStatus.CONFLICT);
			}
			if(_profesor.findByName(profesor.getNombre())!= null){
				return new ResponseEntity(new CustomErrorType("El profesor "+profesor.getNombre()+" ya existe"), HttpStatus.CONFLICT);
			}
			_profesor.saveProfesor(profesor);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uriComponentBuilder.path("/v1/profesores/{id}").buildAndExpand(profesor.getId_profesor()).toUri());
			return new ResponseEntity<String>(headers,HttpStatus.OK);
	}
	
	//DELETE
	@RequestMapping(value="/profesores/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<?>deleteProfesores(@PathVariable("id") int id){
		
		if(id <= 0){
			return new ResponseEntity(new CustomErrorType("Debe ingresar un ID"), HttpStatus.CONFLICT);
		}
		
		Profesor profesor = _profesor.findById(id);
		if(profesor == null){
			return new ResponseEntity(new CustomErrorType("No se encontró este profesor con ID "+ id), HttpStatus.NOT_FOUND);
		}
		
		_profesor.deleteProfesorById(id);
		return new ResponseEntity<Profesor>(HttpStatus.NO_CONTENT);
	}
	
	//SUBIR AVATAR
	
	public static final String PROFESOR_UPLOADED_FOLDER = "images/profesores/";
	
	@RequestMapping(value="/profesores/images", method=RequestMethod.POST, headers=("content-type=multipart/form-data"))
	public ResponseEntity<byte[]> uploadProfesorImage(@RequestParam("id_profesor") int idProfesor, @RequestParam("file") MultipartFile multipartfile, UriComponentsBuilder ucBuilder){
		if(idProfesor <= 0){
			return new ResponseEntity(new CustomErrorType("Id del profesor es requerido "), HttpStatus.NO_CONTENT);
		}
		
		if(multipartfile.isEmpty()){
			return new ResponseEntity(new CustomErrorType("Por favor seleccione una foto para subir "), HttpStatus.NO_CONTENT);			
		}
		
		Profesor profe = _profesor.findById(idProfesor);
		if(profe==null){
			return new ResponseEntity(new CustomErrorType("Este ID "+idProfesor+" no existe"), HttpStatus.NOT_FOUND);
		}
		
		if(!profe.getAvatar().isEmpty() || profe.getAvatar() != null){
			String fileName = profe.getAvatar();
			Path path = Paths.get(fileName);
			File f = path.toFile();
			if(f.exists()){
				f.delete();
			}
		}
		
		try{
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
			String dateName = dateFormat.format(date);
			
			String fileName = String.valueOf(idProfesor)+"-pictureProfesor-"+dateName+"." + multipartfile.getContentType().split("/")[1];
			profe.setAvatar(PROFESOR_UPLOADED_FOLDER + fileName);
			
			byte[] bytes = multipartfile.getBytes();
			Path path = Paths.get(PROFESOR_UPLOADED_FOLDER+ fileName);
			Files.write(path,bytes);
			
			_profesor.updateProfesor(profe);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
		
		}catch(Exception ex){
			ex.printStackTrace();
			return new ResponseEntity(new CustomErrorType("Error durante la subida de la foto: " + multipartfile.getOriginalFilename()),HttpStatus.CONFLICT);
		}
	}
	
	
	//GET IMAGE
	@RequestMapping(value="/profesores/{id_profesor}/images", method=RequestMethod.GET)
	public ResponseEntity<byte[]> getProfesoresImage(@PathVariable("id_profesor") int idProfesor){
		if(idProfesor <= 0){
			return new ResponseEntity(new CustomErrorType("Id del profesor es requerido "), HttpStatus.NO_CONTENT);
		}
		
		Profesor profe = _profesor.findById(idProfesor);
		if(profe == null){
			return new ResponseEntity(new CustomErrorType("Profesor con ID "+idProfesor+" no fue encontrado "), HttpStatus.NOT_FOUND);
		}
		
		try{
			
			String fileName = profe.getAvatar();
			Path path = Paths.get(fileName);
			File f = path.toFile();
			if(!f.exists()){
				return new ResponseEntity(new CustomErrorType("Imagen no encontrada"), HttpStatus.CONFLICT);
			}
			
			byte[] image = Files.readAllBytes(path);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return new ResponseEntity(new CustomErrorType("Error al mostrar la imagen "), HttpStatus.CONFLICT);
		}		
	}
	
	//DELETE IMAGE
	@RequestMapping(value="/profesores/{id_profesor}/images", method=RequestMethod.DELETE, headers="Accept=application/json")
	public ResponseEntity<?> deleteProfesorImage(@PathVariable("id_profesor") int idProfesor){
		if(idProfesor <= 0){
			return new ResponseEntity(new CustomErrorType("Id del profesor es requerido "), HttpStatus.NO_CONTENT);
		}
		
		Profesor profe = _profesor.findById(idProfesor);
		if(profe == null){
			return new ResponseEntity(new CustomErrorType("Profesor con ID "+ idProfesor+" no fue encontrado"), HttpStatus.CONFLICT);
		}
		
		if(profe.getAvatar().isEmpty() || profe.getAvatar()==null){
			return new ResponseEntity(new CustomErrorType("No tiene imagen asignada este profesor"), HttpStatus.NO_CONTENT);
		}
		
		String fileName = profe.getAvatar();
		Path path =Paths.get(fileName);
		File file = path.toFile();
		
		if(file.exists()){
			file.delete();
		}
		
		profe.setAvatar("");
		_profesor.updateProfesor(profe);
	
		return new ResponseEntity<Profesor>(HttpStatus.NO_CONTENT);
	}
	
	//RELACIÓN DE RECURSOS 1 PROFESOR TIENE MUCHAS REDES SOCIALES
	@RequestMapping(value="profesores/redesSocial", method=RequestMethod.PATCH, headers="Accept=application/json")
	public ResponseEntity<?> assignProfesorRedesSociales(@RequestBody Profesor profe, UriComponentsBuilder uriComponentBuilder){
		Integer idProfe = new Integer(profe.getId_profesor());
		
		if(idProfe == null || idProfe <= 0)
		{
			return new ResponseEntity(new CustomErrorType("Se necesita el ID del profesor, ID de red social y nickname"), HttpStatus.NOT_FOUND);
		}
		
		Profesor profeSave = _profesor.findById(idProfe);
		if(profeSave == null)
		{
			return new ResponseEntity(new CustomErrorType("ID de profesor "+idProfe+" no se encontró"), HttpStatus.NOT_FOUND);
		}

		if(profe.getProfesorRedesSociales().size() == 0)
		{
			return new ResponseEntity(new CustomErrorType("Necesitamos el ID del profesor, ID de red social y NICKNAME "+idProfe+" no se encontró"), HttpStatus.NOT_FOUND);
		}
		else{
			
			Iterator<ProfesorRedesSociales> i = profe.getProfesorRedesSociales().iterator();
		
			while(i.hasNext()){
				ProfesorRedesSociales profesorRedesSocial = i.next();			
				if(profesorRedesSocial.getRedes().getId_red_social() <= 0 || profesorRedesSocial.getNick_name() == null){
					
					return new ResponseEntity(new CustomErrorType("Error: necesitamos el ID del profesor, ID de red social y nickname"), HttpStatus.NOT_FOUND);
				
				}else{
					ProfesorRedesSociales prsAux = _redes.findByIdRedByIdAndNombre(profesorRedesSocial.getRedes().getId_red_social(), profesorRedesSocial.getNick_name());
					
					if(prsAux != null){
						return new ResponseEntity(new CustomErrorType("La red social con nickname "+profesorRedesSocial.getNick_name()+" ya existe"), HttpStatus.NO_CONTENT);
					}
					RedesSociales redes = _redes.findById(profesorRedesSocial.getRedes().getId_red_social());
					
					if(redes == null){
						return new ResponseEntity(new CustomErrorType("El id_red_social no se encontró"), HttpStatus.NOT_FOUND);
					}
					
					profesorRedesSocial.setRedes(redes);
					profesorRedesSocial.setProfesor(profeSave);
				
					if(prsAux == null){
						profeSave.getProfesorRedesSociales().add(profesorRedesSocial);						
					}else{
						LinkedList<ProfesorRedesSociales> profesorRedesSociales = new LinkedList<>();
						profesorRedesSociales.addAll(profeSave.getProfesorRedesSociales());
						for (int j = 0; j < profesorRedesSociales.size(); j++) {
							ProfesorRedesSociales prs2 = profesorRedesSociales.get(j);
							if(profesorRedesSocial.getProfesor().getId_profesor() == prs2.getProfesor().getId_profesor() && profesorRedesSocial.getRedes().getId_red_social() == prs2.getRedes().getId_red_social()){
							
								prs2.setNick_name(profesorRedesSocial.getNick_name());
								
								profesorRedesSociales.set(j, prs2);
								
							}else{
								profesorRedesSociales.set(j, prs2);
							}
						}
						
						profeSave.getProfesorRedesSociales().clear();
						profeSave.getProfesorRedesSociales().addAll(profesorRedesSociales);
											
					}
				}								
			}
		}
		_profesor.updateProfesor(profeSave);
		return new ResponseEntity<Profesor>(profeSave, HttpStatus.OK);
	
	}
}



