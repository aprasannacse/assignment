package com.rabo.pims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="User")
@Data

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="name")
	private String name;
	
	/*
	 * @Column(name="email") private String email;
	 * 
	 * @Column(name="mobile") private String mobile;
	 */
	@Column(name="password")
	private String passowrd;
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role roles;
	
		
}
