package com.hardcode.catalogoprofesores.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.hardcode.catalogoprofesores.model.RedesSociales;
import com.hardcode.catalogoprofesores.service.RedesSocialService;
import com.hardcode.catalogoprofesores.util.CustomErrorType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1")
public class RedesSocialesController {
	
	@Autowired
	RedesSocialService _redSocial; 
	//GET
	@RequestMapping(value="/redesSocial", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<RedesSociales>> getRedesSociales(@RequestParam(value="name", required=false) String name){
		List<RedesSociales> redSocial = new ArrayList<>();
		
		if(name == null){
		
			redSocial = _redSocial.getAllRedes();
			if(redSocial.isEmpty()){
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<RedesSociales>>(redSocial,HttpStatus.OK);
		
		}else{
			RedesSociales redesSocial = _redSocial.findByName(name);
			if(redesSocial == null){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			
			redSocial.add(redesSocial);
			return new ResponseEntity<List<RedesSociales>>(redSocial,HttpStatus.OK);
			
		}				
	}
	
	//GET ID
	@RequestMapping(value="/redesSocial/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<RedesSociales> getRedSocialId(@PathVariable("id") Long idRedSocial){
		if(idRedSocial == null || idRedSocial <= 0){
			return new ResponseEntity(new CustomErrorType("ID de la red social es requerido"), HttpStatus.CONFLICT);
		}
		
		RedesSociales redSocial = _redSocial.findById(idRedSocial);
		if(redSocial == null){
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<RedesSociales>(redSocial, HttpStatus.OK); 
	
	}
	
	//POST
	@RequestMapping(value="/redesSocial", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?>createRedSocial(@RequestBody RedesSociales redSocial, UriComponentsBuilder uriComponent){
		if(redSocial.getNombre_red_social().equals(null) || redSocial.getNombre_red_social().isEmpty()){
			return new ResponseEntity(new CustomErrorType("Nombre de la red social es requerido"), HttpStatus.CONFLICT);
		}
		if(_redSocial.findByName(redSocial.getNombre_red_social())!= null){
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		_redSocial.saveRedesSociales(redSocial);
		RedesSociales redSocial2 = _redSocial.findByName(redSocial.getNombre_red_social());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponent.path("v1/redesSocial/{id}").buildAndExpand(redSocial2.getId_red_social()).toUri());
		
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	//Update
	
	@RequestMapping(value="/redesSocial/{id}", method = RequestMethod.PATCH, headers = "Accept=application/json")
	public ResponseEntity<?>updateRedesSocial(@PathVariable("id") Long idRedSocial, @RequestBody RedesSociales redSocial){
		
		if(idRedSocial == null || idRedSocial <= 0){
			return new ResponseEntity(new CustomErrorType("ID de la red social es requerido"), HttpStatus.CONFLICT);
		}
		
		RedesSociales currentRedSocial = _redSocial.findById(idRedSocial);
		if(currentRedSocial == null){
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		currentRedSocial.setNombre_red_social(redSocial.getNombre_red_social());
		currentRedSocial.setIcono(redSocial.getIcono());
		
		_redSocial.updateRedesSociales(currentRedSocial);
		return new ResponseEntity<RedesSociales>(currentRedSocial, HttpStatus.OK);
	}
	
	//DELETE
	@RequestMapping(value="/redesSocial/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<?>deleteRedSocial(@PathVariable("id") Long idRedSocial){
		if(idRedSocial == null || idRedSocial <= 0){
			return new ResponseEntity(new CustomErrorType("ID de la red social es requerido"), HttpStatus.CONFLICT);
		}
		
		RedesSociales redSocial = _redSocial.findById(idRedSocial);
		if(redSocial == null){
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		_redSocial.deleteRedesSociales(idRedSocial);
		return new ResponseEntity<RedesSociales>(HttpStatus.OK);
	}
	
	public static final String RED_SOCIAL_UPLOADER_FOLDER="images/red_social/";
	//SUBIR ICONO DE REDES SOCIALES
	@RequestMapping(value="/redesSocial/images", method = RequestMethod.POST, headers=("content-type=multipart/form-data"))
	public ResponseEntity<byte[]>uploadRedesSocialImage(@RequestParam("id_red_social") Long idRedSocial, @RequestParam("file") MultipartFile multipartFile, UriComponentsBuilder ucBuilder){
		if(idRedSocial == null){
			return new ResponseEntity(new CustomErrorType("Por ingrese el ID de la red social a modificar"), HttpStatus.NO_CONTENT);
		}
		
		if(multipartFile.isEmpty()){
			return new ResponseEntity(new CustomErrorType("Por favor selecciona una foto"), HttpStatus.NO_CONTENT);
		}
		
		RedesSociales redSocial = _redSocial.findById(idRedSocial);
		if(redSocial == null){
			return new ResponseEntity(new CustomErrorType("Red Social no encontrada"), HttpStatus.NOT_FOUND);
		}
		
		if(!redSocial.getIcono().isEmpty() || redSocial.getIcono()!= null){
			String fileName = redSocial.getIcono();
			Path path = Paths.get(fileName);
			File f = path.toFile();
			if(f.exists()){
				f.delete();	
			}
		}
		
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String dateName = sdf.format(date);
			
			String fileName = String.valueOf(idRedSocial)+"-pictureRedSocial-"+dateName+"."+ multipartFile.getContentType().split("/")[1];
			redSocial.setIcono(RED_SOCIAL_UPLOADER_FOLDER+fileName);
			
			byte[] bytes = multipartFile.getBytes();
			Path path = Paths.get(RED_SOCIAL_UPLOADER_FOLDER + fileName);
			Files.write(path, bytes);
			
			_redSocial.updateRedesSociales(redSocial);
			
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return new ResponseEntity(new CustomErrorType(""+ex.getMessage())+multipartFile.getOriginalFilename(), HttpStatus.NO_CONTENT);
		}
		
	}
	
	
	
}




