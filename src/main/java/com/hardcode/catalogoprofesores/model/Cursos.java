package com.hardcode.catalogoprofesores.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cursos")
public class Cursos implements Serializable{
	
	@Id
	@Column(name="id_curso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_curso;
	
	@Column(name="proyecto")
	private String proyecto;
	
	@Column(name="tema")
	private String tema;
	
	@Column(name="nombre_curso")
	private String nombre_curso;
	
	@ManyToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="id_profesor")
	private Profesor profesor;
	
	public Cursos(){
		super();
	}
		
	public Cursos(String proyecto, String tema, String nombre_curso) {
		super();
		this.proyecto = proyecto;
		this.tema = tema;
		this.nombre_curso = nombre_curso;
	}

	public int getId_curso() {
		return id_curso;
	}

	public void setId_curso(int id_curso) {
		this.id_curso = id_curso;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getNombre_curso() {
		return nombre_curso;
	}

	public void setNombre_curso(String nombre_curso) {
		this.nombre_curso = nombre_curso;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
		
}
