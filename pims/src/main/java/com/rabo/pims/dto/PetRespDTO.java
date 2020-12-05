package com.rabo.pims.dto;

import com.rabo.pims.entity.PetEntity;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class PetRespDTO {

	private Integer id;

	private String name;

	private Integer age;

	private Integer person_id;

	public PetRespDTO( PetEntity petEntity) {
		super();	
		this.id = petEntity.getPet_id();
		this.name = petEntity.getName();
		this.age = petEntity.getAge();
		if(petEntity.getPersonEntity()!= null) {
			this.person_id = petEntity.getPersonEntity().getPerson_id();
		}
	}




}
