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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="profesor_redes_sociales")
public class ProfesorRedesSociales implements Serializable{
	
	@Id
	@Column(name="id_profesor_red_social")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_profesor_red_social;
	
	@Column(name="nick_name")
	private String nick_name;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_profesor")
	@JsonIgnore
	private Profesor profesor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_red_social")
	private RedesSociales redes;
	
	
	public ProfesorRedesSociales(){
		super();
	}

	public ProfesorRedesSociales(Profesor profesor, RedesSociales redes, String nickname) {
		super();
		this.profesor = profesor;
		this.redes = redes;
		this.nick_name = nickname;
	}

	public int getId_profesor_red_social() {
		return id_profesor_red_social;
	}

	public void setId_profesor_red_social(int id_profesor_red_social) {
		this.id_profesor_red_social = id_profesor_red_social;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public RedesSociales getRedes() {
		return redes;
	}

	public void setRedes(RedesSociales redes) {
		this.redes = redes;
	}
	
	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	
}
