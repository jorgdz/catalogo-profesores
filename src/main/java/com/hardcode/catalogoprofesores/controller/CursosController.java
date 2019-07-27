package com.hardcode.catalogoprofesores.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.hardcode.catalogoprofesores.model.Cursos;
import com.hardcode.catalogoprofesores.model.Profesor;
import com.hardcode.catalogoprofesores.service.CursosService;
import com.hardcode.catalogoprofesores.service.ProfesoresService;
import com.hardcode.catalogoprofesores.util.CustomErrorType;

@Controller
@RequestMapping("/v1")
@CrossOrigin
public class CursosController {
	
	@Autowired
	private CursosService _cursos;
	
	@Autowired
	private ProfesoresService _profesores;
	
	//GET
	@RequestMapping(value="/cursos",method = RequestMethod.GET, headers="Accept=Application/json")
	public ResponseEntity<List<Cursos>>getAllCursos(@RequestParam(value="name", required=false) String name, @RequestParam(value="id_profesor", required=false) Integer id_profesor){
		List<Cursos> cursos = new ArrayList<>();
				
		if(id_profesor != null)
		{
			cursos = _cursos.findByIdProfesor(id_profesor);
			if(cursos.isEmpty()){
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
		}
		
		if(name != null)
		{
			Cursos curso = _cursos.findByName(name);
			if(curso == null){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			cursos.add(curso);		
		}
		
		if(name == null && id_profesor == null)
		{
			cursos = _cursos.findAllCursos();
			if(cursos.isEmpty()){
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
		}
		
		return new ResponseEntity<List<Cursos>>(cursos,HttpStatus.OK);
	}

	//GET ID
	@RequestMapping(value="/cursos/{id}", method=RequestMethod.GET, headers="Accept=Application/json")
	public ResponseEntity<Cursos> getCursosById(@PathVariable("id") int id){
		Cursos cursos = _cursos.findById(id);
		if(cursos == null){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cursos>(cursos, HttpStatus.OK);
	}
	
	//CREATE
	@RequestMapping(value="/cursos", method=RequestMethod.POST, headers="Accept=Application/json") 	
	public ResponseEntity<?>createCurso(@RequestBody Cursos curso, UriComponentsBuilder ucBuilder){
		if(_cursos.findByName(curso.getNombre_curso()) != null){
			return new ResponseEntity(new CustomErrorType("Curso "+curso.getNombre_curso()+" ya existente"), HttpStatus.CONFLICT);
		}
		_cursos.saveCursos(curso);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/v1/cursos/{id}").buildAndExpand(curso.getId_curso()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	//UPDATE
	@RequestMapping(value="/cursos/{id}", method=RequestMethod.PATCH, headers="Accept=Application/json")
	public ResponseEntity<?>updateCurso(@PathVariable("id") int id, @RequestBody Cursos cursos){
		Cursos currentCurso = _cursos.findById(id);
		if(currentCurso == null){
			return new ResponseEntity(new CustomErrorType("Este curso no existe"), HttpStatus.NOT_FOUND);
		}
		
		currentCurso.setNombre_curso(cursos.getNombre_curso());
		currentCurso.setProyecto(cursos.getProyecto());
		currentCurso.setTema(cursos.getTema());
		_cursos.updateCursos(currentCurso);
		return new ResponseEntity<Cursos>(currentCurso, HttpStatus.OK);
	}
	
	//DELETE
	@RequestMapping(value="/cursos/{id}", method=RequestMethod.DELETE, headers="Accept=Application/json")
	public ResponseEntity<?>deleteCursos(@PathVariable("id") int id){
		Cursos cursos = _cursos.findById(id);
		if(cursos == null){
			return new ResponseEntity(new CustomErrorType("Este curso no existe"), HttpStatus.NOT_FOUND);
		}
		_cursos.deleteCursos(id);
		return new ResponseEntity<Cursos>(HttpStatus.NO_CONTENT);
	}
	
	//ASIGNAR UN PROFESOR A UN CURSO
	@RequestMapping(value="cursos/profesores", method=RequestMethod.PATCH, headers="Accept=Application/json")
	public ResponseEntity<Cursos> asignarProfesorCurso(@RequestBody Cursos cursos, UriComponentsBuilder ucBuilder){
		if(cursos.getId_curso() <= 0 || cursos.getProfesor().getId_profesor() <= 0){
			return new ResponseEntity(new CustomErrorType("Se necesita el ID del curso y del profesor"), HttpStatus.NOT_FOUND);
		}
		Cursos cursoSave =_cursos.findById(cursos.getId_curso());
		if(cursoSave == null){
			return new ResponseEntity(new CustomErrorType("No se encontro resultados"), HttpStatus.NOT_FOUND);
		}
		Profesor profe = _profesores.findById(cursos.getProfesor().getId_profesor()); 
		if(profe == null){
			return new ResponseEntity(new CustomErrorType("Se ha producido un error"), HttpStatus.CONFLICT);
		}
		cursoSave.setProfesor(profe);
		_cursos.updateCursos(cursoSave);
		
		return new ResponseEntity<Cursos>(cursoSave,HttpStatus.OK);
	}
	
}
