package com.hardcode.catalogoprofesores.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="redes_sociales")
public class RedesSociales implements Serializable{
	
	@Id
	@Column(name="id_red_social")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_red_social;
	
	@Column(name="nombre_red_social")
	private String nombre_red_social;
	
	@Column(name="icono")
	private String icono;
	
	@OneToMany
	@JoinColumn(name="id_red_social")
	@JsonIgnore
	private Set<ProfesorRedesSociales> profesorRedesSociales;
		
	public RedesSociales(){
		super();
	}
	
	public RedesSociales(String nombre_red_social, String icono) {
		super();
		this.nombre_red_social = nombre_red_social;
		this.icono = icono;
	}

	public Long getId_red_social() {
		return id_red_social;
	}

	public void setId_red_social(Long id_red_social) {
		this.id_red_social = id_red_social;
	}

	public String getNombre_red_social() {
		return nombre_red_social;
	}

	public void setNombre_red_social(String nombre_red_social) {
		this.nombre_red_social = nombre_red_social;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}	
	
	public Set<ProfesorRedesSociales> getProfesorRedesSociales() {
		return profesorRedesSociales;
	}

	public void setProfesorRedesSociales(Set<ProfesorRedesSociales> profesorRedesSociales) {
		this.profesorRedesSociales = profesorRedesSociales;
	}

		
}
