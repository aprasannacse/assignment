package com.rabo.pims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import java.util.List;

@Entity
@Table(name="PERSON_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(name="person_seq", sequenceName="person_seq",allocationSize = 1)
	@Column(name="id")
	private int person_id;	
	
	@Column(name="First_Name")
	private String firstName;
	
	@Column(name="Last_Name")
	private String lastName;
	
	@Column(name="Birth_date")
	private String dob;
	
	@Column(name="Current_Addr")
	private String currentAddress;

	@OneToMany()
	@JoinColumn(name="person_id")
	private List < PetEntity > Pets;
	
}
