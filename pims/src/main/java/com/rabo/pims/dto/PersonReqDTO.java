package com.rabo.pims.dto;

import com.rabo.pims.entity.PersonEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonReqDTO {

	private int id;

	private String firstName;

	private String lastName;

	private String dob;

	private String currentAddress;

	private PersonEntity person;

		public PersonEntity getPerson(PersonReqDTO personReqEntity) {
		person = new PersonEntity();
		person.setFirstName(personReqEntity.getFirstName());
		person.setLastName(personReqEntity.getLastName());
		person.setDob(personReqEntity.getDob());
		person.setCurrentAddress(personReqEntity.getCurrentAddress());
		return person;
	}

	public PersonReqDTO(PersonEntity personEntity) {	
		super();
		this.id = personEntity.getPerson_id();
		this.firstName = personEntity.getFirstName();
		this.lastName = personEntity.getLastName();
		this.dob = personEntity.getDob();
		this.currentAddress = personEntity.getCurrentAddress();
	}

}
