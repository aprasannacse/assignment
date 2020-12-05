package com.rabo.pims.service;

import java.util.List;
import java.util.Optional;

import com.rabo.pims.dto.PersonRespDTO;
import com.rabo.pims.entity.PersonEntity;

public interface PersonService {
	
	public PersonRespDTO addPerson(PersonEntity person);
	public List<PersonRespDTO> getAllPersons();
	public PersonRespDTO getPersonById(Integer id);
	public PersonRespDTO updateAddress(PersonEntity newPerson,int id);
	public List<PersonRespDTO> getPersonByName(String name);
	public Optional<List<PersonEntity>> checkNameExists(String firstName,String lastName);
}
