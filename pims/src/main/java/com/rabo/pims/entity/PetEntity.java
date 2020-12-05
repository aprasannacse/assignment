package com.rabo.pims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name="PET_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetEntity {
	
	@Id
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_seq")
    @SequenceGenerator(name="pet_seq", sequenceName="pet_seq",allocationSize = 1)
	@Column(name="id")
	private int pet_id;	
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Age")
	private int age;
		
	@ManyToOne()
    @JoinColumn(name = "person_id")
	@JsonIgnore
    private PersonEntity personEntity;

}
