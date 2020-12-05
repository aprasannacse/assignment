package com.rabo.pims.dto;

import com.rabo.pims.entity.PetEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetReqDTO {	

	private int id;	

	private String name;

	private int age;

	private PetEntity petEntity;

	public PetReqDTO(String name, int age,PetEntity petEntity ) {
		super();
		this.name = name;
		this.age = age;
		this.petEntity=petEntity;
	}	 
	public PetEntity getPetEntity() {
		petEntity = new PetEntity();
		petEntity.setPet_id(this.id);
		petEntity.setName(this.name);
		petEntity.setAge(this.age);
		petEntity.setPersonEntity(this.petEntity.getPersonEntity());
		return petEntity;
	}

}
