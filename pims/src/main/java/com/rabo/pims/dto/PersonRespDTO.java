package com.rabo.pims.dto;

import java.util.List;

import com.rabo.pims.entity.PersonEntity;
import com.rabo.pims.entity.PetEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRespDTO {

	private Integer id;

	private String firstName;

	private String lastName;

	private String dob;

	private String currentAddress;

	private  List < PetEntity > pets;

	public PersonRespDTO(PersonEntity personEntity) {	
		super();
		this.id = personEntity.getPerson_id();
		this.firstName = personEntity.getFirstName();
		this.lastName = personEntity.getLastName();
		this.dob = personEntity.getDob();
		this.currentAddress = personEntity.getCurrentAddress();
		if(null != personEntity.getPets() && !(personEntity.getPets().isEmpty())) {
			this.pets = personEntity.getPets();
		}else {
			this.pets = null;
		}

	}

}
