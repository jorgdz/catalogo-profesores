package com.hardcode.catalogoprofesores.model;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="profesor")
public class Profesor implements Serializable{
	
	@Id
	@Column(name="id_profesor")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_profesor;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="avatar")
	private String avatar;
	
	@OneToMany(mappedBy="profesor")
	@JsonIgnore
	private Set<Cursos> cursos;	
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="id_profesor")
	private Set<ProfesorRedesSociales> profesorRedesSociales;
		
	public Profesor(){
		super();
	}
	
	public Profesor(String nombre, String avatar) {
		super();
		this.nombre = nombre;
		this.avatar = avatar;
	}
	
	public Long getId_profesor() {
		return id_profesor;
	}
	public void setId_profesor(Long id_profesor) {
		this.id_profesor = id_profesor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public Set<ProfesorRedesSociales> getProfesorRedesSociales() {
		return profesorRedesSociales;
	}

	public void setProfesorRedesSociales(Set<ProfesorRedesSociales> profesorRedesSociales) {
		this.profesorRedesSociales = profesorRedesSociales;
	}

	public Set<Cursos> getCursos() {
		return cursos;
	}

	public void setCursos(Set<Cursos> cursos) {
		this.cursos = cursos;
	}

}
